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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService {


        private UserRepository userRepository;
        private PasswordEncoder passwordEncoder;
        private final UserMapper userMapper = UserMapper.INSTANCE;
        private final Logger logger = LoggerFactory.getLogger(UserService.class);
        private TokenService tokenService;

        public MessageResponseDTO saveUser(UserDTO userDTO) {
                try {   userDTO.setPassword(generatePasswordEncoder(userDTO.getPassword()));
                        userDTO.setName(userDTO.getName().toLowerCase(Locale.ROOT));
                        userDTO.setEmail(userDTO.getEmail().toLowerCase(Locale.ROOT));
                        userRepository.saveAndFlush(userMapper.toModel(userDTO));
                        return createMessageRespose("User with id " + userDTO.getId() + " was created/updated!");
                }catch (DataIntegrityViolationException e) {
                                String errorMessage = "User already registered";
                                return createMessageRespose(errorMessage);
                }catch (ConstraintViolationException e) { //email nao preenchido
                        String errorMessage = "Error creating new user, verify your e-mail. " ;
                        return createMessageRespose(errorMessage);}
                catch(Exception e ){
                        String errorMessage = "Error creating new user:";
                        return createMessageRespose(errorMessage);
                }
        }

        private String generatePasswordEncoder(String password){
                String teste = this.passwordEncoder.encode(password);
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
                Optional<User> userOptional = userRepository.findByEmail(email.toLowerCase());
                User user = userOptional.orElseThrow(() -> new UserNotFoundException(email));
                if (validPassword(user, password)){
                        log.debug("valid Password");
                        Token token = new Token(tokenService.createToken(user));
                        log.debug("sending token");
                        return ResponseEntity.ok(token);
                }else{
                        return ResponseEntity.status(403).build();
                }
        }

}

