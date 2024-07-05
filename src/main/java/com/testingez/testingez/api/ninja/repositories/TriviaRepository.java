package com.testingez.testingez.api.ninja.repositories;

import com.testingez.testingez.api.ninja.entities.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaRepository extends JpaRepository<Trivia, Long> {
}
