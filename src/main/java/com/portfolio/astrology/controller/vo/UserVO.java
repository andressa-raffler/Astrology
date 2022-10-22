package com.portfolio.astrology.controller.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.astrology.model.Astrology;
import lombok.Data;
import java.time.LocalDate;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO {
    private String name;
    private LocalDate birthDate;
    private Integer birthHour;
    private Integer birthMinute;
    private String city;
    private String state;
  //  private Astrology astrology;
}
