package com.portfolio.astrology;

import com.portfolio.astrology.service.StartIdSequence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AstrologyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstrologyApplication.class, args);
        StartIdSequence.start();
    }

}
