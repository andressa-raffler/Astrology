package com.portfolio.astrology.controller.dto;

import lombok.Data;

import java.util.TimeZone;

@Data
public class TimeZoneDTO {
   String id;//": "Europe/Paris",
   String name;//": "GMT+01:00",
   String offsetString;//": "01:00:00",
   Integer offsetMinutes;//": 60
}
