package com.testingez.mainService.repository;

import com.testingez.mainService.model.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByTestIdAndNumber(Long testId, Integer number);

    List<Question> findAllByTestId(Long testId);

}
