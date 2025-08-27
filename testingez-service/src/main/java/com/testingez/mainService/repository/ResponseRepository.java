package com.testingez.mainService.repository;

import com.testingez.mainService.model.entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {

    List<Response> findAllByTestIdAndUserId(Long testId, Long userId);

    Optional<Response> findByTestIdAndQuestionIdAndUserId(Long testId, Long questionId, Long userId);

}
