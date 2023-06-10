package com.portfolio.astrology.service;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.security.Token;
import com.portfolio.astrology.security.TokenService;
import com.portfolio.astrology.security.ValidationCode;
import com.portfolio.astrology.security.ValidationCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.validation.*;
import java.util.*;
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
        private ValidationCodeService validationCodeService;
        private EmailSenderService emailSenderService;
        private ValidationCode validationCode;
        private UserDTO userDTO;
        public MessageResponseDTO saveUser(UserDTO userDTO) {
                try { if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
                        throw new DataIntegrityViolationException("Error. Email already registered.");
                }
                      MessageResponseDTO listOfViolationsMessageResponseDTO = inputedUserErrorMessages(userDTO);
                      if(listOfViolationsMessageResponseDTO == null){
                             userDTO.setPassword(generatePasswordEncoder(userDTO.getPassword()));
                             userDTO.setName(userDTO.getName().toLowerCase(Locale.ROOT));
                             userDTO.setEmail(userDTO.getEmail().toLowerCase(Locale.ROOT));
                             this.userDTO = userDTO;
                             validationCode = validationCodeService.generateValidationCode();
                             emailSenderService.sendEmail(userDTO.getEmail(), validationCode.getCode()+"");
                             return createMessageResponse("ok");
                      }
                      listOfViolationsMessageResponseDTO.setMessage("Error. Please verify your validation code");
                      return listOfViolationsMessageResponseDTO;
                }catch (DataIntegrityViolationException e) {
                    return createMessageResponse("Error. Email already exists.");
                }
                catch(Exception e ){
                    return createMessageResponse("Error creating new user");
                }
        }
        public MessageResponseDTO emailValidate(String stringCode){
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(stringCode, JsonObject.class);
                int code = jsonObject.get("validationCode").getAsInt();
                if(validationCodeService.isValidationCodeValid(code)) {
                        persistUser(this.userDTO);
                        return createMessageResponse("ok");
                }
                return createMessageResponse("Error. Please insert a valid code");
        }


        private MessageResponseDTO persistUser(UserDTO userDTO){
                Long id = saveAndFlushPerson(userMapper.toModel(userDTO));
                return createMessageResponse("User with id " + id + " was created/updated!");

        }

        private Long saveAndFlushPerson(User userToSave) {
                User savedUser = userRepository.saveAndFlush(userToSave);
                return savedUser.getId();
        }



        private String generatePasswordEncoder(String password){
                String teste = this.passwordEncoder.encode(password);
               return this.passwordEncoder.encode(password);
        }

        public MessageResponseDTO updateUserById(Long id, UserDTO updatedDTOUser) throws UserNotFoundException {
                getUserFromRepository(id);
           //     saveUser(updatedDTOUser);
                return createMessageResponse("User with id " + id + " was updated!");
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

        private MessageResponseDTO createMessageResponse(String message){
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


        private MessageResponseDTO inputedUserErrorMessages(UserDTO userDTO) {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<User>> violations = validator.validate(userMapper.toModel(userDTO));
                if (!violations.isEmpty()) {
                        List<String> errorMessages = new ArrayList<>();
                        for (ConstraintViolation<User> violation : violations) {
                                errorMessages.add(violation.getMessage());
                        }
                        return createMessageResponse(errorMessages.toString());
                }
                return null;
        }



}

