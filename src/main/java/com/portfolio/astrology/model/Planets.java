//package com.portfolio.astrology.model;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "planets")
//public class Planets {
//        @Id
//        @GeneratedValue(strategy = GenerationType.SEQUENCE)
//        private Long id;
//
//        @OneToMany(mappedBy = "planets")
//        private List<Px> pxList;
//
////        @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p0;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p1;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p2;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p3;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p4;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p5;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p6;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p7;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p8;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p9;
////
////        @OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
////        Px p11;
//
//}
