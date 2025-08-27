package com.testingez.mainService.repositories;

import com.testingez.mainService.models.entities.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByTestIdAndUserId(Long testId, Long userId);

    Integer countByTestId(Long testId);

    Page<Result> findAllByUserId(Long userId, Pageable pageable);

    List<Result> findAllByTestIdOrderByPointsDesc(Long testId);

}
