package com.portfolio.astrology.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetsResponse {
    P1Response p1;
    P2Response p2;
    P3Response p3;
    P4Response p4;
    P5Response p5;
    P6Response p6;
    P7Response p7;
    P8Response p8;
    P9Response p9;
    P10Response p10;
    P11Response p11;
}
