package com.portfolio.astrology.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "astrology")
public class Astrology implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "astrology", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Planet> planets = new ArrayList<>();

    protected void setPlanets(List<Planet> planets) {
        this.planets = planets;
        }
        public void addToPlanets(Planet planet) {
        planet.setAstrology(this);
        this.planets.add(planet);
        }



}
