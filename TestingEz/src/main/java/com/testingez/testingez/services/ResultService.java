package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.ResultSummaryDTO;
import com.testingez.testingez.models.dtos.exp.ResultDetailsDTO;

public interface ResultService {

    void calculateResult(Long testId, Long userId);

    ResultSummaryDTO getResultSummary(Long testId, Long userId);

    ResultDetailsDTO getResultDetails(Long resultId);

}
