package com.testingez.testingez.api.ninja.repositories;

import com.testingez.testingez.api.ninja.entities.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Long> {
}
