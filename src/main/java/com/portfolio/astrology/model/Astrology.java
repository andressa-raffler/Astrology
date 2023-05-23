package com.portfolio.astrology.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "astrology_seq")
    @SequenceGenerator(name = "astrology_seq", sequenceName = "astrology_seq", allocationSize = 1)
    private Long id;

    @Column(length = 400)
    private String shortDescription;

    @Column
    private boolean shortDescriptionCalculated = true;

    @OneToMany(mappedBy = "astrology", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Planet> planets = new ArrayList<>();
    protected void setPlanets(List<Planet> planets) {
        this.planets = planets;
        }
    public void addToPlanets(Planet planet) {
        planet.setAstrology(this);
        this.planets.add(planet);
        }

    @OneToMany(mappedBy = "astrology", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT) //to solve this problem: org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags
    private List<House> houses = new ArrayList<>();

    protected void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public void addToHouses(House house) {
        house.setAstrology(this);
        this.houses.add(house);
    }


}
