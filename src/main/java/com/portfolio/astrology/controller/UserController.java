package com.portfolio.astrology.controller;



import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.security.Token;
import com.portfolio.astrology.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/astrology/v1/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findUserById(@PathVariable("id") Long id, HttpServletRequest request) throws UserNotFoundException {
        return userService.findById(id, request);
    }

    @GetMapping
    public List<UserDTO> listAllUsers() {
        return userService.listAllUsers();
    }

    @PostMapping("/singn-up")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO saveNewUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateUserById(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.updateUserById(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponseDTO deleteUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.generateToken(userDTO.getEmail(), userDTO.getPassword());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException (MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>() ;
        ex.getBindingResult().getAllErrors().forEach( (error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
            return errors;
        }
    }
