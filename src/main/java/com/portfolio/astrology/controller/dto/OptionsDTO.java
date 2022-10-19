package com.portfolio.astrology.controller.dto;

import lombok.Data;
import java.util.ArrayList;


@Data
public class OptionsDTO {
    String zodiacType;
    boolean zodiacName;
    String positions;
    String coordinates;
    boolean astrometric;
    String houseSystem;
    boolean progressed;
    boolean derived;
    boolean return1; //palavra reservada no java, como proceder?
    String planetsDate;
    String housesDate;
    ArrayList<String> displayOptions;

}
