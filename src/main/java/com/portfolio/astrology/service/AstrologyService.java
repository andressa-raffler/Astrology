package com.portfolio.astrology.service;

import com.portfolio.astrology.controller.dto.AstrologyDTO;
import com.portfolio.astrology.model.Astrology;
import com.portfolio.astrology.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Service
@Slf4j
public class AstrologyService {


    @Autowired
    UserRepository userRepository;

    UserService userService;

    public Astrology getChartByDate(Integer birthYear, Integer birthMonth, Integer birthDay, Integer birthHour, Integer birthMinute, String queryLocation,
                                    int houses) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Astrology> response = restTemplate.getForEntity(
                "https://api.astrologico.org/v1/chart?localdate=" +
                        birthDay + "|" + birthMonth + "|" +
                        birthYear + "|" + birthHour +
                        "|"+ birthMinute + "&querylocation=" + queryLocation +
                        "&houses=" + houses + "&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46",
                Astrology.class);
        return response.getBody(); //IMPLEMENTAR CHAMADO E CONSUMO DA API APARTIR DA DATA DE NASCIMENTO
    }
}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples