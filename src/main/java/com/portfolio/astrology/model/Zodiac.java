package com.portfolio.astrology.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Zodiac {

    ARIES ("Aries", 0L, 30L),
    TAURUS ("Taurus", 30L, 60L),
    GEMINI ("Gemini",60L,90L),
    CANCER ("Cancer",90L,120L),
    LEO ("Leo",120L,150L),
    VIRGO ("Virgo",150L,180L),
    LIBRA ("Libra",180L,210L),
    SCORPIO ("Scorpio",210L,240L),
    SAGITTARIUS ("Sagittarius",240L,270L),
    CAPRICORN ("Capricorn",270L,300L),
    AQUARIUS ("Aquarius",300L,330L),
    PISCES ("Pisces",330L,360L);


    private String description;
    private long minLongitude;
    private long maxLongitude;

    Zodiac(String description, long minLongitude, long maxLongitude) {
        this.description = description;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }

    public String getDescription() {
        return description;
    }

    public long getMinLongitude() {
        return minLongitude;
    }

    public long getMaxLongitude() {
        return maxLongitude;
    }

//    public String getZodiacByLongitude(long longitude){
//        Zodiac[] zodiacList = Zodiac.values();
//        for (Zodiac zodiac: zodiacList) {
//            if (zodiac.getMinLongitude() <= longitude && longitude < zodiac.getMaxLongitude()){
//                return zodiac.getDescription();
//            }
//        }
//        return null;
//    }


}
