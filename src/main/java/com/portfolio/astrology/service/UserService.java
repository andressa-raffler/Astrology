package com.portfolio.astrology.service;


import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.response.AstrologyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private AstrologyService astrologyService;

        public User saveUser(User user){
                return this.userRepository.saveAndFlush(user);
        }

        public User updateUserById(Long id, User updatedUser){
                Optional<User> optionalUser = this.userRepository.findById(id);
                if(optionalUser.isPresent()){
                        User user = optionalUser.get();
                        updatedUser.setId(user.getId());
                        updatedUser.setName(user.getName());
                        updatedUser.setBirthDate(user.getBirthDate());
                        updatedUser.setBirthHour(user.getBirthHour());
                        updatedUser.setBirthMinute(user.getBirthMinute());
                        updatedUser.setCity(user.getCity());
                        updatedUser.setState(user.getState());
                        user.setAstrology(getAstrologyFromAstrologyResponse(Objects.requireNonNull(astrologyService.getChartByDate(
                                user.getBirthYear(user.getBirthDate()), user.getBirthMonth(user.getBirthDate()),
                                user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
                                user.getCity() + " " + user.getState(), 15).block(Duration.ofSeconds(30)))));
                        return this.userRepository.save(updatedUser);
                }
           return null; //melhorar esse retorno
        }

        public Optional<User> findById(Long id){
                return this.userRepository.findById(id);
        }

        public void deleteUserById(Long id) {
                this.userRepository.deleteById(id);
        }

        public List<User> listAllUsers() {
                return this.userRepository.findAll();
        }

//        public Integer getDay(LocalDate birthDate){
//                return birthDate.getDayOfYear();
//        }
//
//        public Integer getMonth(LocalDate birthDate){
//                return birthDate.getMonthValue();
//        }
//
//        public Integer getYear(LocalDate birthDate){
//                return birthDate.getYear();
//        }
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
}
