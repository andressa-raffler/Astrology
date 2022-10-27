package com.portfolio.astrology.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Astrology")
public class Astrology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String status;

    @OneToOne(fetch=FetchType.LAZY)
    private Planets planets;





}
