package com.portfolio.astrology.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.astrology.model.Astrology;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private String name;
    private LocalDate birthDate;
    private Integer birthHour;
    private Integer birthMinute;
    private String city;
    private String state;
    private Astrology astrology;

}

