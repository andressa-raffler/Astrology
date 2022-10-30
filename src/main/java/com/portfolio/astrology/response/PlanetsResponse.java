package com.portfolio.astrology.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PlanetsResponse {
    P0Response P0;
    P1Response P1;
    P2Response P2;
    P3Response P3;
    P4Response P4;
    P5Response P5;
    P6Response P6;
    P7Response P7;
    P8Response P8;
    P9Response P9;
    P11Response P11;
}
