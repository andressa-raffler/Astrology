package com.portfolio.astrology.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Planet")
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "astrology_id")
    private Astrology astrology;

}
