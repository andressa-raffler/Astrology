package com.portfolio.astrology.repository;

import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.model.Astrology;
import com.portfolio.astrology.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(
            value = """
                    \t
                       select people.* from users \s
                    \t left join people on users.id = people.users_id\s
                    \t where users.email = ?1
                    """,
            nativeQuery = true)
    Collection<Person> findAllPersonFromOneUser(String email);


    default void updatePersonAstrology(Long persoId, Astrology astrology) throws PersonNotFoundException {
        Person person = findById(persoId).orElseThrow(() -> new PersonNotFoundException("Person not found"));
        person.setAstrology(astrology);
        saveAndFlush(person);
    }



}
