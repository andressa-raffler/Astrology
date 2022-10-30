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

import java.util.ArrayList;
import java.util.List;
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
  //      astrology.setStatus(astrologyResponse.getStatusResponse());
        List<Planet> planets = new ArrayList<Planet>();

        Planet p0 = new Planet();
        p0.setName(astrologyResponse.getPlanets().getP0().getName());
        p0.setLongitude(astrologyResponse.getPlanets().getP0().getLongitude());
        p0.setLongitudeSpeed(astrologyResponse.getPlanets().getP0().getLongitudeSpeed());
        planets.add(0,p0);


        Planet p1 = new Planet();
        p1.setName(astrologyResponse.getPlanets().getP1().getName());
        p1.setLongitude(astrologyResponse.getPlanets().getP1().getLongitude());
        p1.setLongitudeSpeed(astrologyResponse.getPlanets().getP1().getLongitudeSpeed());
        planets.add(1,p1);

        Planet p2 = new Planet();
        p2.setName(astrologyResponse.getPlanets().getP2().getName());
        p2.setLongitude(astrologyResponse.getPlanets().getP2().getLongitude());
        p2.setLongitudeSpeed(astrologyResponse.getPlanets().getP2().getLongitudeSpeed());
        planets.add(2,p2);

        Planet p3 = new Planet();
        p3.setName(astrologyResponse.getPlanets().getP3().getName());
        p3.setLongitude(astrologyResponse.getPlanets().getP3().getLongitude());
        p3.setLongitudeSpeed(astrologyResponse.getPlanets().getP3().getLongitudeSpeed());
        planets.add(3,p3);

        Planet p4 = new Planet();
        p4.setName(astrologyResponse.getPlanets().getP4().getName());
        p4.setLongitude(astrologyResponse.getPlanets().getP4().getLongitude());
        p4.setLongitudeSpeed(astrologyResponse.getPlanets().getP4().getLongitudeSpeed());
        planets.add(4,p4);

        Planet p5 = new Planet();
        p5.setName(astrologyResponse.getPlanets().getP5().getName());
        p5.setLongitude(astrologyResponse.getPlanets().getP5().getLongitude());
        p5.setLongitudeSpeed(astrologyResponse.getPlanets().getP5().getLongitudeSpeed());
        planets.add(5,p5);

        Planet p6 = new Planet();
        p6.setName(astrologyResponse.getPlanets().getP6().getName());
        p6.setLongitude(astrologyResponse.getPlanets().getP6().getLongitude());
        p6.setLongitudeSpeed(astrologyResponse.getPlanets().getP6().getLongitudeSpeed());
        planets.add(6,p6);

        Planet p7 = new Planet();
        p7.setName(astrologyResponse.getPlanets().getP7().getName());
        p7.setLongitude(astrologyResponse.getPlanets().getP7().getLongitude());
        p7.setLongitudeSpeed(astrologyResponse.getPlanets().getP7().getLongitudeSpeed());
        planets.add(7,p7);

        Planet p8  = new Planet();
        p8.setName(astrologyResponse.getPlanets().getP8().getName());
        p8.setLongitude(astrologyResponse.getPlanets().getP8().getLongitude());
        p8.setLongitudeSpeed(astrologyResponse.getPlanets().getP8().getLongitudeSpeed());
        planets.add(8,p8);

        Planet p9 = new Planet();
        p9.setName(astrologyResponse.getPlanets().getP9().getName());
        p9.setLongitude(astrologyResponse.getPlanets().getP9().getLongitude());
        p9.setLongitudeSpeed(astrologyResponse.getPlanets().getP9().getLongitudeSpeed());
        planets.add(9,p9);


        Planet p11 = new Planet();
        p11.setName(astrologyResponse.getPlanets().getP11().getName());
        p11.setLongitude(astrologyResponse.getPlanets().getP11().getLongitude());
        p11.setLongitudeSpeed(astrologyResponse.getPlanets().getP11().getLongitudeSpeed());
        planets.add(10,p11);

        astrology.setPlanets(planets);

        return astrology;
    }

}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples