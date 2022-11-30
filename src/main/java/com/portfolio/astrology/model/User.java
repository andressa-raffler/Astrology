package com.portfolio.astrology.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "The name field is required")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "The email field is required")
    @Email(message = "Invalid email")
//    @UniqueElements(message = "Email is already registered")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "The password field is required")
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT) //to solve this problem: org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags
    private List<Person> people = new ArrayList<>();

    protected void setPeople(List<Person> people) {
        this.people = people;
    }

    public void addToUsers(Person person) {
        person.setUser(this);
        this.people.add(person);
    }



}
