package com.portfolio.astrology.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "planets")
public class Planets {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P0 p0;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P1 p1;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P2 p2;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P3 p3;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P4 p4;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P5 p5;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P6 p6;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P7 p7;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P8 p8;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P9 p9;

        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
        P11 p11;

}
