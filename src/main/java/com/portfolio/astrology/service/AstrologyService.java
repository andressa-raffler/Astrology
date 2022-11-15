package com.portfolio.astrology.service;


import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.response.AstrologyResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Service
//@Slf4j
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
                        "|" + birthMinute + "&querylocation=" + queryLocation +
                        "&houses=" + houses + "&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46",
                AstrologyResponse.class);
        return getAstrologyFromAstrologyResponse(Objects.requireNonNull(response.getBody()));

    }

    private Astrology getAstrologyFromAstrologyResponse(AstrologyResponse astrologyResponse) {
        Astrology astrology = new Astrology();
        //      astrology.setStatus(astrologyResponse.getStatusResponse());

        Planet p0 = new Planet();
        p0.setName(astrologyResponse.getPlanets().getP0().getName());
        p0.setLongitude(astrologyResponse.getPlanets().getP0().getLongitude());
        p0.setLongitudeSpeed(astrologyResponse.getPlanets().getP0().getLongitudeSpeed());
        astrology.addToPlanets(p0);

        Planet p1 = new Planet();
        p1.setName(astrologyResponse.getPlanets().getP1().getName());
        p1.setLongitude(astrologyResponse.getPlanets().getP1().getLongitude());
        p1.setLongitudeSpeed(astrologyResponse.getPlanets().getP1().getLongitudeSpeed());
        astrology.addToPlanets(p1);

        Planet p2 = new Planet();
        p2.setName(astrologyResponse.getPlanets().getP2().getName());
        p2.setLongitude(astrologyResponse.getPlanets().getP2().getLongitude());
        p2.setLongitudeSpeed(astrologyResponse.getPlanets().getP2().getLongitudeSpeed());
        astrology.addToPlanets(p2);

        Planet p3 = new Planet();
        p3.setName(astrologyResponse.getPlanets().getP3().getName());
        p3.setLongitude(astrologyResponse.getPlanets().getP3().getLongitude());
        p3.setLongitudeSpeed(astrologyResponse.getPlanets().getP3().getLongitudeSpeed());
        astrology.addToPlanets(p3);

        Planet p4 = new Planet();
        p4.setName(astrologyResponse.getPlanets().getP4().getName());
        p4.setLongitude(astrologyResponse.getPlanets().getP4().getLongitude());
        p4.setLongitudeSpeed(astrologyResponse.getPlanets().getP4().getLongitudeSpeed());
        astrology.addToPlanets(p4);

        Planet p5 = new Planet();
        p5.setName(astrologyResponse.getPlanets().getP5().getName());
        p5.setLongitude(astrologyResponse.getPlanets().getP5().getLongitude());
        p5.setLongitudeSpeed(astrologyResponse.getPlanets().getP5().getLongitudeSpeed());
        astrology.addToPlanets(p5);

        Planet p6 = new Planet();
        p6.setName(astrologyResponse.getPlanets().getP6().getName());
        p6.setLongitude(astrologyResponse.getPlanets().getP6().getLongitude());
        p6.setLongitudeSpeed(astrologyResponse.getPlanets().getP6().getLongitudeSpeed());
        astrology.addToPlanets(p6);

        Planet p7 = new Planet();
        p7.setName(astrologyResponse.getPlanets().getP7().getName());
        p7.setLongitude(astrologyResponse.getPlanets().getP7().getLongitude());
        p7.setLongitudeSpeed(astrologyResponse.getPlanets().getP7().getLongitudeSpeed());
        astrology.addToPlanets(p7);

        Planet p8 = new Planet();
        p8.setName(astrologyResponse.getPlanets().getP8().getName());
        p8.setLongitude(astrologyResponse.getPlanets().getP8().getLongitude());
        p8.setLongitudeSpeed(astrologyResponse.getPlanets().getP8().getLongitudeSpeed());
        astrology.addToPlanets(p8);

        Planet p9 = new Planet();
        p9.setName(astrologyResponse.getPlanets().getP9().getName());
        p9.setLongitude(astrologyResponse.getPlanets().getP9().getLongitude());
        p9.setLongitudeSpeed(astrologyResponse.getPlanets().getP9().getLongitudeSpeed());
        astrology.addToPlanets(p9);


        Planet p11 = new Planet();
        p11.setName(astrologyResponse.getPlanets().getP11().getName());
        p11.setLongitude(astrologyResponse.getPlanets().getP11().getLongitude());
        p11.setLongitudeSpeed(astrologyResponse.getPlanets().getP11().getLongitudeSpeed());
        astrology.addToPlanets(p11);

        House house1 = new House();
        house1.setName("House 1");
        house1.setLongitude(astrologyResponse.getHouses().getHouses().getHouse1().getLongitude());
        house1.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse1().getLongitudeSpeed());
        astrology.addToHouses(house1);

        House house2 = new House();
        house2.setName("House 2");
        house2.setLongitude(astrologyResponse.getHouses().getHouses().getHouse2().getLongitude());
        house2.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse2().getLongitudeSpeed());
        astrology.addToHouses(house2);

        House house3 = new House();
        house3.setName("House 3");
        house3.setLongitude(astrologyResponse.getHouses().getHouses().getHouse3().getLongitude());
        house3.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse3().getLongitudeSpeed());
        astrology.addToHouses(house3);

        House house4 = new House();
        house4.setName("House 4");
        house4.setLongitude(astrologyResponse.getHouses().getHouses().getHouse4().getLongitude());
        house4.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse4().getLongitudeSpeed());
        astrology.addToHouses(house4);

        House house5 = new House();
        house5.setName("House 5");
        house5.setLongitude(astrologyResponse.getHouses().getHouses().getHouse5().getLongitude());
        house5.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse5().getLongitudeSpeed());
        astrology.addToHouses(house5);

        House house6 = new House();
        house6.setName("House 6");
        house6.setLongitude(astrologyResponse.getHouses().getHouses().getHouse6().getLongitude());
        house6.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse6().getLongitudeSpeed());
        astrology.addToHouses(house6);

        House house7 = new House();
        house7.setName("House 7");
        house7.setLongitude(astrologyResponse.getHouses().getHouses().getHouse7().getLongitude());
        house7.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse7().getLongitudeSpeed());
        astrology.addToHouses(house7);

        House house8 = new House();
        house8.setName("House 8");
        house8.setLongitude(astrologyResponse.getHouses().getHouses().getHouse8().getLongitude());
        house8.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse8().getLongitudeSpeed());
        astrology.addToHouses(house8);

        House house9 = new House();
        house9.setName("House 9");
        house9.setLongitude(astrologyResponse.getHouses().getHouses().getHouse9().getLongitude());
        house9.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse9().getLongitudeSpeed());
        astrology.addToHouses(house9);

        House house10 = new House();
        house10.setName("House 10");
        house10.setLongitude(astrologyResponse.getHouses().getHouses().getHouse10().getLongitude());
        house10.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse10().getLongitudeSpeed());
        astrology.addToHouses(house10);

        House house11 = new House();
        house11.setName("House 11");
        house11.setLongitude(astrologyResponse.getHouses().getHouses().getHouse11().getLongitude());
        house11.setLongitudeSpeed(astrologyResponse.getHouses().getHouses().getHouse11().getLongitudeSpeed());
        astrology.addToHouses(house11);

        House ascendant = new House();
        ascendant.setName("Ascendant");
        ascendant.setLongitude(astrologyResponse.getHouses().getAscendant().getLongitude());
        ascendant.setLongitudeSpeed(astrologyResponse.getHouses().getAscendant().getLongitudeSpeed());
        astrology.addToHouses(ascendant);

        House mc = new House();
        mc.setName("Mid-heaven");
        mc.setLongitude(astrologyResponse.getHouses().getMc().getLongitude());
        mc.setLongitudeSpeed(astrologyResponse.getHouses().getMc().getLongitudeSpeed());
        astrology.addToHouses(mc);

        return astrology;
    }


}

