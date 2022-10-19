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

@Getter
@Setter
@Service
@Slf4j
public class AstrologyService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    String urlPagina = getPageUrl();
    public Astrology getChartByDate(LocalDate birthDate, String queryLocation,
                                    int hosues) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AstrologyDTO>response = restTemplate.getForEntity(
                "https://api.astrologico.org/v1/chart?localdate=" +
                userService.getDay(birthDate) + "|" + userService.getMonth(birthDate)+ "|" +
                        userService.getYear(birthDate) + "|" + userService.getHour(birthDate) +
                        "|"userService.getMinute(birthDate) + "&querylocation=" +
                        cidade" "estado +
                "&houses="house"&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46",
                AstrologyDTO.class);

       // private final WebClient webClient;
       // public Flux<>



        return new Astrology(); //IMPLEMENTAR CHAMADO E CONSUMO DA API APARTIR DA DATA DE NASCIMENTO



    }

    public String getPageUrl(){
        assert userService != null;
        String url = "https://api.astrologico.org/v1/chart?localdate=" +
               userService.getDay()"|" userService.getMonth()"|"userService.getYear()"|"hora"|"minuto"&querylocation="cidade" "estado+
            "&houses="house"&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46";

    }
}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples