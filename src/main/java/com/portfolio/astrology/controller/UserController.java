package com.portfolio.astrology.controller;


import com.portfolio.astrology.model.*;
import com.portfolio.astrology.response.AstrologyResponse;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



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
                                    schema = @Schema(implementation = User.class))
                    }
            )

    })

    @GetMapping("/save-new-user")
    public ResponseEntity<Astrology> saveNewUser( @RequestBody User user) {
//        User user = new User();
//        user.setName(name);
//        user.setBirthDate(birthDate);
//        user.setBirthHour(birthHour);
//        user.setBirthMinute(birthMinute);
//        user.setCity(city);
//        user.setState(state);
        user.setAstrology(astrologyService.getChartByDate(user.getBirthYear(user.getBirthDate()), user.getBirthMonth(user.getBirthDate()), user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
                user.getCity() + " " + user.getState(), 15));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("get-user-by-id/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = this.userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("get-all-users")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> userList = this.userService.listAllUsers();
        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(userList);
    }


    @PutMapping("update-user-by-id/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        if (Objects.nonNull(user)) {
            User updatedUser = this.userService.updateUserById(id, user);
            return ResponseEntity.ok().body(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("delete-user-by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        Optional<User> optionalUser = this.userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            this.userService.deleteUserById(user.getId());
            return ResponseEntity.ok("User removed");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


//    private void setUserFromUserDTO(User user, UserDTO userDTO) {
//        userDTO.setName(user.getName());
//        userDTO.setBirthDate(user.getBirthDate());
//        userDTO.setBirthHour(user.getBirthHour());
//        userDTO.setBirthMinute(user.getBirthMinute());
//        userDTO.setCity(user.getCity());
//        userDTO.setState(user.getState());
//        userDTO.setAstrology(astrologyService.getChartByDate(user.getBirthYear(user.getBirthDate()),user.getBirthMonth(user.getBirthDate()),user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
//                user.getCity()+" "+ user.getState(), 15));
//    }


}
