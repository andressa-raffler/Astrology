package com.portfolio.astrology.controller;

import com.portfolio.astrology.controller.dto.UserDTO;
import com.portfolio.astrology.controller.vo.UserVO;
import com.portfolio.astrology.model.User;
import com.portfolio.astrology.service.AstrologyService;
import com.portfolio.astrology.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AstrologyService astrologyService;

    @Operation(summary = "Save new user", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))
                    }
            )

    })

    @PostMapping("/save-new-user")
    public ResponseEntity<String>saveNewUser (@Valid @RequestBody UserVO userVO){
        User user = new User();
        user.setName(userVO.getName());
        user.setBirthDate(userVO.getBirthDate());
        user.setCity(userVO.getCity());
        user.setState(userVO.getState());
        user.setAstrology(astrologyService.getByDate(userVO.getBirthDate()));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("get-user-by-id/{id}")
    public ResponseEntity<UserDTO>findUserById(@PathVariable("id") Long id){
        Optional<User> optionalUser = this.userService.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setBirthDate(user.getBirthDate());
            userDTO.setCity(user.getCity());
            userDTO.setState(user.getState());
            userDTO.setAstrology(astrologyService.getByDate(user.getBirthDate()));
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("get-all-users")
    public ResponseEntity<List<UserDTO>> listAllUsers(){
        List<User> userList = this.userService.listAllUsers();
        if(userList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<UserDTO> userDTOList = userList.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setName(user.getName());
                    userDTO.setBirthDate(user.getBirthDate());
                    userDTO.setCity(user.getCity());
                    userDTO.setState(user.getState());
                    userDTO.setAstrology(astrologyService.getByDate(user.getBirthDate()));
                    return userDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOList);
    }


    @PutMapping("update-user-by-id/{id}")
    public ResponseEntity<UserDTO>updateUserById(@PathVariable("id") Long id, @RequestBody UserVO userVO){
        User user = this.userService.updateUserById(id, userVO);
        if(Objects.nonNull(user)){
            UserDTO userDTO = new UserDTO();
            userDTO.setName(userVO.getName());
            userDTO.setBirthDate(userVO.getBirthDate());
            userDTO.setCity(userVO.getCity());
            userDTO.setState(userVO.getState());
            userDTO.setAstrology(astrologyService.getByDate(userVO.getBirthDate()));
            return ResponseEntity.ok().body(userDTO);
        }
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("delete-user-by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        Optional<User> optionalUser = this.userService.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            this.userService.deleteUserById(user.getId());
            return ResponseEntity.ok("User removed");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
