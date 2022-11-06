package com.portfolio.astrology.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlanetsResponse {
    PxResponse P0;
    PxResponse P1;
    PxResponse P2;
    PxResponse P3;
    PxResponse P4;
    PxResponse P5;
    PxResponse P6;
    PxResponse P7;
    PxResponse P8;
    PxResponse P9;
    PxResponse P11;
}
