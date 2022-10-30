package com.portfolio.astrology.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "P1")
public class P1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 100)
    Long longitude;//": 92.15322965365729

    @Column(nullable = false, length = 100)
    Long longitudeSpeed;//": 0.9535995114940861


}
