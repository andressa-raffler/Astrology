package com.portfolio.astrology.model;


import com.portfolio.astrology.controller.dto.PlanetsDTO;
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

    @OneToOne(fetch=FetchType.LAZY)
    private Planets planets;




}
