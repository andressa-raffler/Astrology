package com.portfolio.astrology.response.astrology;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HousesResponse {
    Houses2Response houses;
    HouseXResponse ascendant;
    HouseXResponse mc;

}

