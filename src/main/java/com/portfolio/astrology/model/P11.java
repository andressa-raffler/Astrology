package com.portfolio.astrology.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class P11 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 100)
    Long longitude;//": 92.15322965365729

    @Column(nullable = false, length = 100)
    Long longitudeSpeed;//": 0.9535995114940861


}
