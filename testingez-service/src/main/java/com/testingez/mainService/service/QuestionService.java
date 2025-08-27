package com.testingez.mainService.service;

import com.testingez.mainService.model.dtos.exp.AnsweredQuestionDTO;
import com.testingez.mainService.model.dtos.exp.QuestionAnswerDTO;
import com.testingez.mainService.model.dtos.exp.QuestionDetailsDTO;
import com.testingez.mainService.model.dtos.imp.QuestionCreateDTO;
import com.testingez.mainService.model.dtos.imp.QuestionEditDTO;
import com.testingez.mainService.model.dtos.imp.TestQuestionsDTO;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

public interface QuestionService {

    void putDown(TestQuestionsDTO testQuestionsDTO, Long testId);

    Integer getQuestionsCountOfTheTest(Long testId);

    QuestionAnswerDTO fetchQuestionData(Long testId, Integer questionNumber);

    List<AnsweredQuestionDTO> getAnsweredQuestionsData(Long resultId);

    List<QuestionDetailsDTO> getQuestionsOfATest(Long testId);

    QuestionEditDTO fetchQuestionData(Long questionId);

    Boolean edit(QuestionEditDTO questionEditDTO);

    Boolean add(Long testId, QuestionCreateDTO questionData);

    Boolean delete(Long questionId, Long testId);

    LocalDateTime getOrSetStartTime(Long testId, Integer questionNumber, HttpSession session);
}
