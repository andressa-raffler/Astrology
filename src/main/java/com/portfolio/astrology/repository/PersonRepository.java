package com.portfolio.astrology.repository;

import com.portfolio.astrology.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findTopByName(String name);

    void deleteByName(String name);
}
