package com.dio.devacademy.domain.repository;

import com.dio.devacademy.domain.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    boolean existsByEmail(String email);

    boolean existsByGithubUsername(String githubUsername);

    Optional<Developer> findByEmail(String email);

    Optional<Developer> findByGithubUsername(String githubUsername);
}
