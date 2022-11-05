package com.portfolio.astrology.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Houses2Response {
    HouseXResponse house1;
    HouseXResponse house2;
    HouseXResponse house3;
    HouseXResponse house4;
    HouseXResponse house5;
    HouseXResponse house6;
    HouseXResponse house7;
    HouseXResponse house8;
    HouseXResponse house9;
    HouseXResponse house10;
    HouseXResponse house11;
    HouseXResponse house12;

}

