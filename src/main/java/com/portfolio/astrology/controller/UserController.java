package com.portfolio.astrology.controller;



import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/astrology/v1/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    UserService userService;

    @GetMapping("/{id}") //VERIFICAR O HASH PARA ENCODIFICAR O ID
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.findById(id);
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
    public MessageResponseDTO validatePassword(@RequestBody String email, String password) throws UserNotFoundException {
        return userService.validatePassword(email, password);

    }

}
