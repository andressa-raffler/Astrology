package com.portfolio.astrology.controller.dto;

import lombok.Data;

@Data
public class AstrologyDTO {
    String status;
    MetadataDTO metadata;
    PlanetsDTO planets;
}
