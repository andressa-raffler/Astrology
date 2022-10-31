package com.portfolio.astrology.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 100)
    Long longitude;

    @Column(nullable = false, length = 100)
    Long longitudeSpeed;

    @ManyToOne(fetch=FetchType.LAZY)
    private Astrology astrology;

}
