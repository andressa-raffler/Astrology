package com.portfolio.astrology.repository;

import com.portfolio.astrology.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    @Disabled
    void userShouldBeSaved(){
    //given
        User user = new User();
        String email = "andressa@email.com";
        user.setEmail(email);
        user.setName("andressa");
        user.setPassword("senha");
        underTest.save(user);
    //when
        Optional<User> expected = underTest.findByEmail(email);
    //then
        assertThat(expected).isPresent();
    }

    @Test
    void userShouldntBeSaved(){
        String email = "andressa@email.com";
        Optional<User> expected = underTest.findByEmail(email);
        assertThat(expected).isEmpty();
    }



}