package com.portfolio.astrology.controller.dto;

import lombok.Data;

@Data
public class LocalDateDTO {

    int day;
    int month;
    int year;
    int hour;
    int minute;
    int second;
    int milisecond;
}
