package com.portfolio.astrology.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "astrology")
public class Astrology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "astrology", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Planet> planets;



}
