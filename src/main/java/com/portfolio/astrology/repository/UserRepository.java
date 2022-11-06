package com.portfolio.astrology.repository;

import com.portfolio.astrology.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findTopByName(String name);

    void deleteByName(String name);
}
