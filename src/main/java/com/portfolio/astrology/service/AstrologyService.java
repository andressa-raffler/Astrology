package com.portfolio.astrology.service;


import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.response.AstrologyResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Getter
@Setter
@Service
@Slf4j
public class AstrologyService {

    private final WebClient webClient;

    @Autowired
    UserRepository userRepository;

    public AstrologyService(WebClient.Builder builder) {
        webClient = builder.baseUrl("https://api.astrologico.org/v1/chart?localdate=").build();
    }


    public Mono<AstrologyResponse> getChartByDate(int birthYear, int birthMonth, int birthDay, int birthHour, int birthMinute, String queryLocation,
                                                  int houses) {
        log.info("Searching natal chart for - [{}]", birthYear+"-"+birthMonth+"-"+birthDay);
        return webClient
                .get()
                .uri(birthDay + "|" + birthMonth + "|" +
                        birthYear + "|" + birthHour +
                        "|"+ birthMinute + "&querylocation=" + queryLocation +
                        "&houses=" + houses + "&key=83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46"
                )
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Please check the parameters provided")))
                .bodyToMono(AstrologyResponse.class);
    }



}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples