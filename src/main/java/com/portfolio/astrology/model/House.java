package com.portfolio.astrology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "house")
public class House implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column( length = 100)
    String name;

    @Column(nullable = false, length = 100)
    float longitude;

    @Column(nullable = false, length = 100)
    float longitudeSpeed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "astrology_id", referencedColumnName = "id")
    @JsonIgnore
    private Astrology astrology;

}


