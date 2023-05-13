package com.portfolio.astrology.repository;

import com.portfolio.astrology.StartIdSequenceBDTests;
import com.portfolio.astrology.model.Person;
import com.portfolio.astrology.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PersonRepositoryTest {


    @Autowired
    private PersonRepository underTest;

    @Autowired
    private UserRepository userRepositoryTest;

    @BeforeEach
    void setUp(){
        StartIdSequenceBDTests.startIdSequence();
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepositoryTest.deleteAll();
    }

    @Test
    void shouldFindAllPersonFromOneUser() {
        User user = new User();
        String email = "andressa@email.com";
        user.setEmail(email);
        user.setName("andressa");
        user.setPassword("senha");
        userRepositoryTest.save(user);

        Person person = new Person();
        String name = "andressaPerson";
        person.setName(name);
        person.setCity("missal");
        person.setState("parana");
        person.setBirthHour(10);
        person.setBirthMinute(23);
        person.setBirthDate(LocalDate.parse("1994-07-16"));
        person.setUser(user);

        underTest.save(person);

        Collection<Person> expected = underTest.findAllPersonFromOneUser(email);
        String personSavedName =  expected.stream().map(person1 -> person.getName() ).toList().get(0);

        assertThat(expected).asList().isNotNull();
        assertThat(personSavedName).isEqualTo(name);

    }


    @Test
    void shouldntFindAnyPersonFromOneUser() {

        String email = "andressa@email.com";
        Collection<Person> expected = underTest.findAllPersonFromOneUser(email);
        assertThat(expected).asList().isEmpty();

    }

}