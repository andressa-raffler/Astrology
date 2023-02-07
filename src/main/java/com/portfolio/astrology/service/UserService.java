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
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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
                userDTO.setPassword(generatePasswordEncoder(userDTO.getPassword()));
                userRepository.saveAndFlush(userMapper.toModel(userDTO));
                return createMessageRespose("User with id " + userDTO.getId() + " was created/updated!");
        }

        private String generatePasswordEncoder(String password){
               return this.passwordEncoder.encode(password);
        }

        public MessageResponseDTO updateUserById(Long id, UserDTO updatedDTOUser) throws UserNotFoundException {
                getUserFromRepository(id);
                saveUser(updatedDTOUser);
                return createMessageRespose("User with id " + id + " was updated!");
        }

        public UserDTO findById(Long id) throws UserNotFoundException {
                User userSaved = getUserFromRepository(id);
                return userMapper.toDTO(userSaved);
        }

        public MessageResponseDTO deleteUserById(Long id) throws UserNotFoundException {
                User userSaved = getUserFromRepository(id);
                userRepository.delete(userSaved);
                return MessageResponseDTO
                        .builder()
                        .message("User with id: "+id+" was deleted!")
                        .build();
        }

        public List<UserDTO> listAllUsers() {
         //       logger.info("User: "+ getUserLogged()+ "listed all users");
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
        public User getUserFromRepository(Long id) throws UserNotFoundException {
                Optional<User> userOptional = userRepository.findById(id);
                if(userOptional.isPresent()) {
                        return userOptional.get();
                }
                else throw  new UserNotFoundException(id);
        }

        public User getUserFromRepository(String email) throws UserNotFoundException {
                return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException(email));
        }

        private boolean validPassword(User user, String password){
                return passwordEncoder.matches(password, user.getPassword());
        }

        public ResponseEntity<Token> generateToken(String email, String password) throws UserNotFoundException {
                User user = getUserFromRepository(userRepository.findByEmail(email).get().getId());
                if (validPassword(user, password)){
                        Token token = new Token(tokenService.createToken(user));
                        return ResponseEntity.ok(token);
                }else{
                        return ResponseEntity.status(403).build();
                }
        }

//        public ResponseEntity<String> getUserLogged(){
//                Authentication userLogged = SecurityContextHolder.getContext().getAuthentication();
//                if(!(userLogged instanceof AnonymousAuthenticationToken)){
//                        return ResponseEntity.ok(userLogged.getName());
//                }
//                return ResponseEntity.status(404).build();
//        }



}

