package com.portfolio.astrology.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "planet")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 100)
    Long longitude;

    @Column(nullable = false, length = 100)
    Long longitudeSpeed;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="astrology_id",  referencedColumnName = "id")
    private Astrology astrology;

}
