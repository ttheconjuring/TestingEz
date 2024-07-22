package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByCode(String code);

    Page<Test> findAllByCreatorId(Long id, Pageable pageable);

}
