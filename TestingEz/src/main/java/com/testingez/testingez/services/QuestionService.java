package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;

import java.util.List;

public interface QuestionService {

    void putDown(TestQuestionsDTO testQuestionsDTO);

    int getQuestionsCountOfTheLastAddedTest();

    QuestionAnswerDTO fetchQuestionData(Long testId, Integer questionNumber);
}
