package com.portfolio.astrology.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "people")
public class Person implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)

    private Long id;

    @NotBlank(message = "The name field is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "The Birth Date field is required")
    @Column(nullable = false, name = "birth_date", columnDefinition = "DATE")
    @PastOrPresent(message = "The Birth Date must be in the past or present")
    private LocalDate birthDate;

    @NotNull(message = "The Birth hour field is required")
    @Min(value = 1, message = "The Birth hour must be greater than or equal to 1")
    @Max(value = 59, message = "The Birth hour must be less than or equal to 24")
    @Column(nullable = false, length = 2)
    private int birthHour;
    @NotNull(message = "The Birth minute field is required")
    @Min(value = 1, message = "The Birth minute must be greater than or equal to 1")
    @Max(value = 59, message = "The Birth minute must be less than or equal to 59")
    @Column(nullable = false, length = 2)
    private int birthMinute;

    @NotBlank(message = "The city field is required")
    @Column(nullable = false, length = 100)
    private String city;

    @NotBlank(message = "The state field is required")
    @Column(nullable = false, length = 100)
    private String state;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Astrology astrology;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
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
        setUsers(user);
    }




}
