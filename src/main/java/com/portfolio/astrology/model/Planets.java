package com.portfolio.astrology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Planets {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id", nullable = false)
        private Long id;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p0;

        @OneToOne(fetch=FetchType.LAZY)
        P1 p1;

        @OneToOne(fetch=FetchType.LAZY)
        P2 p2;

        @OneToOne(fetch=FetchType.LAZY)
        P3 p3;

        @OneToOne(fetch=FetchType.LAZY)
        P4 p4;

        @OneToOne(fetch=FetchType.LAZY)
        P5 p5;

        @OneToOne(fetch=FetchType.LAZY)
        P6 p6;

        @OneToOne(fetch=FetchType.LAZY)
        P7 p7;

        @OneToOne(fetch=FetchType.LAZY)
        P8 p8;

        @OneToOne(fetch=FetchType.LAZY)
        P9 p9;

        @OneToOne(fetch=FetchType.LAZY)
        P10 p10;

        @OneToOne(fetch=FetchType.LAZY)
        P11 p11;

}
