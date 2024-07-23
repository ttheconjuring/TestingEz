package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.ResultSummaryDTO;
import com.testingez.testingez.models.dtos.exp.ResultDetailsDTO;

import java.util.List;

public interface ResultService {

    void calculateResult(Long testId, Long userId);

    ResultSummaryDTO getResultSummary(Long testId, Long userId);

    ResultDetailsDTO getResultDetails(Long resultId);

    List<AnsweredQuestionDTO> getAnsweredQuestionsData(Long resultId);
}
