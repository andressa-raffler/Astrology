package com.portfolio.astrology.controller.dto;

import lombok.Data;

@Data
public class LocationDTO {

    String queryResult;
    Long longitude;
    Long latitude;
    Long elevation;
}
