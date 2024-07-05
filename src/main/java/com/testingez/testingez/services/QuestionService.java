package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;

public interface QuestionService {

    boolean putDown(TestQuestionsDTO testQuestionsDTO);

    int getQuestionsCountOfTheLastAddedTest();

}
