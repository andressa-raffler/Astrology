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
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Controller
@RequestMapping("/astrology-app")
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
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Astrology> saveNewUser(@RequestBody User user) {
        user.setAstrology(getAstrologyFromAstrologyResponse(Objects.requireNonNull(astrologyService.getChartByDate(
                user.getBirthYear(user.getBirthDate()), user.getBirthMonth(user.getBirthDate()),
                user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
                user.getCity() + " " + user.getState(), 15).block(Duration.ofSeconds(30)))));
        userService.saveUser(user);
        return Mono.empty(); // Verificar como melhorar isso daqui
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

    public Astrology getAstrologyFromAstrologyResponse(AstrologyResponse astrologyResponse) {
        Astrology astrology = new Astrology();
        astrology.setStatus(astrologyResponse.getStatusResponse());
        Planets planets = new Planets();

        P1 p1 = new P1();
        p1.setName(astrologyResponse.getPlanets().getP1().getName());
        p1.setLongitude(astrologyResponse.getPlanets().getP1().getLongitude());
        p1.setLongitudeSpeed(astrologyResponse.getPlanets().getP1().getLongitudeSpeed());
        planets.setP1(p1);

        P2 p2 = new P2();
        p2.setName(astrologyResponse.getPlanets().getP2().getName());
        p2.setLongitude(astrologyResponse.getPlanets().getP2().getLongitude());
        p2.setLongitudeSpeed(astrologyResponse.getPlanets().getP2().getLongitudeSpeed());
        planets.setP2(p2);

        P3 p3 = new P3();
        p3.setName(astrologyResponse.getPlanets().getP3().getName());
        p3.setLongitude(astrologyResponse.getPlanets().getP3().getLongitude());
        p3.setLongitudeSpeed(astrologyResponse.getPlanets().getP3().getLongitudeSpeed());
        planets.setP3(p3);

        P4 p4 = new P4();
        p4.setName(astrologyResponse.getPlanets().getP4().getName());
        p4.setLongitude(astrologyResponse.getPlanets().getP4().getLongitude());
        p4.setLongitudeSpeed(astrologyResponse.getPlanets().getP4().getLongitudeSpeed());
        planets.setP4(p4);

        P5 p5 = new P5();
        p5.setName(astrologyResponse.getPlanets().getP5().getName());
        p5.setLongitude(astrologyResponse.getPlanets().getP5().getLongitude());
        p5.setLongitudeSpeed(astrologyResponse.getPlanets().getP5().getLongitudeSpeed());
        planets.setP5(p5);

        P6 p6 = new P6();
        p6.setName(astrologyResponse.getPlanets().getP6().getName());
        p6.setLongitude(astrologyResponse.getPlanets().getP6().getLongitude());
        p6.setLongitudeSpeed(astrologyResponse.getPlanets().getP6().getLongitudeSpeed());
        planets.setP6(p6);

        P7 p7 = new P7();
        p7.setName(astrologyResponse.getPlanets().getP7().getName());
        p7.setLongitude(astrologyResponse.getPlanets().getP7().getLongitude());
        p7.setLongitudeSpeed(astrologyResponse.getPlanets().getP7().getLongitudeSpeed());
        planets.setP7(p7);

        P8 p8  = new P8();
        p8.setName(astrologyResponse.getPlanets().getP8().getName());
        p8.setLongitude(astrologyResponse.getPlanets().getP8().getLongitude());
        p8.setLongitudeSpeed(astrologyResponse.getPlanets().getP8().getLongitudeSpeed());
        planets.setP8(p8);

        P9 p9 = new P9();
        p9.setName(astrologyResponse.getPlanets().getP9().getName());
        p9.setLongitude(astrologyResponse.getPlanets().getP9().getLongitude());
        p9.setLongitudeSpeed(astrologyResponse.getPlanets().getP9().getLongitudeSpeed());
        planets.setP9(p9);

        P10 p10 = new P10();
        p10.setName(astrologyResponse.getPlanets().getP10().getName());
        p10.setLongitude(astrologyResponse.getPlanets().getP10().getLongitude());
        p10.setLongitudeSpeed(astrologyResponse.getPlanets().getP10().getLongitudeSpeed());
        planets.setP10(p10);

        P11 p11 = new P11();
        p11.setName(astrologyResponse.getPlanets().getP11().getName());
        p11.setLongitude(astrologyResponse.getPlanets().getP11().getLongitude());
        p11.setLongitudeSpeed(astrologyResponse.getPlanets().getP11().getLongitudeSpeed());
        planets.setP11(p11);

        astrology.setPlanets(planets);

        return astrology;
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
