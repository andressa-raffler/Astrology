package com.portfolio.astrology.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "astrology")
public class Astrology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "planet_number", nullable = false)
    private int planetNumber;

    @Column(name = "planet_name", nullable = false)
    private String planetName;

    @Column(name = "longitude", nullable = false, length = 100)
    Long longitude;

    @Column(name = "longitude_speed", nullable = false, length = 100)
    Long longitude;






}
