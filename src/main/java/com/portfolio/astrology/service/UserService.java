package com.portfolio.astrology.service;



import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.security.Token;
import com.portfolio.astrology.security.TokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {


        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;
        private final UserMapper userMapper = UserMapper.INSTANCE;
        private final Logger logger = LoggerFactory.getLogger(UserService.class);
        private TokenService tokenService;



        public MessageResponseDTO saveUser(UserDTO userDTO){
                String encoder = this.passwordEncoder.encode(userDTO.getPassword());
                userDTO.setPassword(encoder);
                User userToSave = userMapper.toModel(userDTO);
                User savedUser = userRepository.saveAndFlush(userToSave);
                return createMessageRespose("User with id " + savedUser.getId() + " was created!");
        }

        public MessageResponseDTO updateUserById(Long id, UserDTO updatedDTOUser) throws UserNotFoundException {
                verifyIfUserExists(id);
                String encoder = this.passwordEncoder.encode(updatedDTOUser.getPassword());
                updatedDTOUser.setPassword(encoder);
                User userToUpdate = userMapper.toModel(updatedDTOUser);
                User updatedUser = userRepository.saveAndFlush(userToUpdate);
                return createMessageRespose("User with id " + id + " was updated!");
        }


        public UserDTO findById(Long id, HttpServletRequest request) throws UserNotFoundException {
                User userSaved = verifyIfUserExists(id);
                return userMapper.toDTO(userSaved);
        }

        public MessageResponseDTO deleteUserById(Long id) throws UserNotFoundException {
                User userSaved = verifyIfUserExists(id);
                userRepository.delete(userSaved);
                return MessageResponseDTO
                        .builder()
                        .message("User with id was deleted!")
                        .build();
        }

        public List<UserDTO> listAllUsers() {
                logger.info("User: "+ getUserLogged()+ "listed all users");
                List<User> allUsers = userRepository.findAll();
                return allUsers.stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList());
        }

        private MessageResponseDTO createMessageRespose(String message){
                return MessageResponseDTO
                        .builder()
                        .message(message)
                        .build();
        }
        public User verifyIfUserExists(Long id) throws UserNotFoundException {
                return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        }

        public User verifyIfUserExists(String email) throws UserNotFoundException {
                return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        }

        private boolean validPassword(User user, String password){
                return passwordEncoder.matches(password, user.getPassword());
        }

        public ResponseEntity<Token> generateToken(String email, String password) throws UserNotFoundException {
                User user = verifyIfUserExists(userRepository.findByEmail(email).get().getId());
                if (validPassword(user, password)){
                        Token token = new Token(tokenService.createToken(user));
                        System.out.println("TOKEN GERADO!!! -----------------------------");
                        return ResponseEntity.ok(token);
                }else{
                        return ResponseEntity.status(403).build();
                }
        }

        public ResponseEntity<String> getUserLogged(){
                Authentication userLogged = SecurityContextHolder.getContext().getAuthentication();
                if(!(userLogged instanceof AnonymousAuthenticationToken)){
                        return ResponseEntity.ok(userLogged.getName());
                }
                return ResponseEntity.status(404).build();
        }



}

