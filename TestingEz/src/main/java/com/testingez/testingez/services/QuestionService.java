package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.exp.QuestionDetailsDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.QuestionEditDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;

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

}
