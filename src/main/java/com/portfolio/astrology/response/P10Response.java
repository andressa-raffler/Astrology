package com.portfolio.astrology.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class P10Response {
    String name;
    Long longitude;
    Long longitudeSpeed;
}
