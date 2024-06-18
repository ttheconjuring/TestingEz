package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("SELECT t FROM Test t ORDER BY t.id DESC")
    Optional<Test> findLastAdded();

}
