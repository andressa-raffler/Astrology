package com.portfolio.astrology.response.astrology;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HouseXResponse {

    long longitude;
    long longitudeSpeed;

}
