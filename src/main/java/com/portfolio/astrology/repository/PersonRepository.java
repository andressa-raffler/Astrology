package com.portfolio.astrology.repository;

import com.portfolio.astrology.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findTopByName(String name);
    void deleteByName(String name);

    @Query(
            value = "select * from users left join people on users.id = people.users_id where users.email = 'andressa.raffer@gmail.com'",
            nativeQuery = true)
    Collection<Person> findAllActiveUsersNative();


}
