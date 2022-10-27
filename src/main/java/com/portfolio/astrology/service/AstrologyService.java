package com.portfolio.astrology.service;


import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.response.AstrologyResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Getter
@Setter
@Service
@Slf4j
public class AstrologyService {


    @Autowired
    UserRepository userRepository;

    public Astrology getChartByDate(int birthYear, int birthMonth, int birthDay, int birthHour, int birthMinute, String queryLocation,
                                            int houses) {


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AstrologyResponse> response = restTemplate.getForEntity(
                "https://api.astrologico.org/v1/chart?localdate=" +
                        birthDay + "|" + birthMonth + "|" +
                        birthYear + "|" + birthHour +
                        "|"+ birthMinute + "&querylocation=" + queryLocation +
                        "&houses=" + houses + "&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46",
                AstrologyResponse.class);


        return getAstrologyFromAstrologyResponse(Objects.requireNonNull(response.getBody()));

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

}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples