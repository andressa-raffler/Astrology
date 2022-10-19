package com.portfolio.astrology.controller.dto;

import lombok.Data;

import java.sql.Time;

@Data
public class DateDTO {
    String input; //: "localdate",
    String calendar; //": "Gregorian",
    Long accuracy;//": 3,
    String ISO; //": "1967-06-24T08:34:00.000Z",
    Long UNIX; //": -79629960000,
    UtcDateDTO UTCDate;
    LocalDateDTO localDate;
    TimeZoneDTO timeZone;
    JdDTO Jd;
    Time siderealTime;//": "02:41:14",
    Time localSiderealTime;//": "02:50:38",
    Long obliquity;//": 23.44544363046999,
    Long sun;//": 92.15322965365729,
    Long moon;//": 299.6588250283716,
    Long ascendant;//": 145.67595473218623


}
