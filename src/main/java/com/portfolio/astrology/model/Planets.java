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
        P0 p1;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p2;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p3;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p4;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p5;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p6;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p7;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p8;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p9;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p10;

        @OneToOne(fetch=FetchType.LAZY)
        P0 p11;

}
