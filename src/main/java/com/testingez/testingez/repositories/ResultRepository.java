package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
