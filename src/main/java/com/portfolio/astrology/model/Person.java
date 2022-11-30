package com.portfolio.astrology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "people")
public class Person implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, name = "birth_date", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(nullable = false, length = 2)
    private int birthHour;

    @Column(nullable = false, length = 2)
    private int birthMinute;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Astrology astrology;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @JsonIgnore
    private User users;

    public int getBirthYear(LocalDate birthDate) {
       return birthDate.getYear();
    }
    public int getBirthMonth(LocalDate birthDate) {
        return birthDate.getMonthValue();
    }
    public int getBirthDay(LocalDate birthDate) {
        return birthDate.getDayOfMonth();
    }


    public void setUser(User user) {

    }
}
