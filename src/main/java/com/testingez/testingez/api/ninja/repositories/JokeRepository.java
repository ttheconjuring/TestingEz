package com.testingez.testingez.api.ninja.repositories;

import com.testingez.testingez.api.ninja.entities.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
