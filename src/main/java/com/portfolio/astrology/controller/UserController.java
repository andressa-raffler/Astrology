package com.portfolio.astrology.controller;


import com.portfolio.astrology.model.*;
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



@Controller
@RequestMapping("/astrology")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AstrologyService astrologyService;


    @PostMapping("/save-new-user")
    public ResponseEntity<Astrology> saveNewUser( @RequestBody User user) {
        user.setAstrology(astrologyService.getChartByDate(user.getBirthYear(user.getBirthDate()), user.getBirthMonth(user.getBirthDate()), user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
                user.getCity() + " " + user.getState(), 15));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("get-user-by-name/{name}")
    public ResponseEntity<User>findUserByName(@PathVariable("name") String name){
        Optional<User> optionalUser = this.userService.findByName(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user); //ESSE PONTO TA DANDO ERRO, MELHORAR O RETORNO
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("update-user-by-name/{name}")
    public ResponseEntity<User> updateUserByName(@PathVariable("name") String name, @RequestBody User user) {
        if (Objects.nonNull(user)) {
            User updatedUser = this.userService.updateUserByName(name, user);
            return ResponseEntity.ok(updatedUser);
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

    @DeleteMapping("delete-user-by-name/{name}")
    public ResponseEntity<String> deleteUserByName(@PathVariable("name") String name) {
        Optional<User> optionalUser = this.userService.findByName(name);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            this.userService.deleteUserById(user.getId());
            return ResponseEntity.ok("User removed");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("get-zodiac-chart/{name}")
    public ResponseEntity<List<String>> getZodiacChart(@PathVariable("name") String name) {
        Optional<User> optionalUser = this.userService.findByName(name);
        if(optionalUser.isPresent()) {
            List<String> zodiacChart = astrologyService.getZodiacChart(name);
            return ResponseEntity.ok(zodiacChart);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
