package com.portfolio.astrology.model;

import lombok.*;
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
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
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
    @Column(nullable = false, length = 100, unique = true)
    private String password;

    @OneToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT) //to solve this problem: org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags
    private List<Person> people = new ArrayList<>();

    protected void setPeople(List<Person> people) {
        this.people = people;
    }

    public void addToUsers(Person person) {
        person.setUser(this);
        people.add(person);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(people, user.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, people);
    }
}
