package com.portfolio.astrology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "planet")
public class Planet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private Long longitude;

    @Column(nullable = false, length = 100)
    private Long longitudeSpeed;

    @Column
    private String position;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="astrology_id",  referencedColumnName = "id")
    @JsonIgnore
    private Astrology astrology;



}
