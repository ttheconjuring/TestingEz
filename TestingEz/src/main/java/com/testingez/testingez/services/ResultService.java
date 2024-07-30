package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.ResultPeekDTO;
import com.testingez.testingez.models.dtos.exp.ResultSummaryDTO;
import com.testingez.testingez.models.dtos.exp.ResultDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResultService {

    void calculateResult(Long testId, Long userId);

    ResultSummaryDTO getResultSummary(Long testId, Long userId);

    ResultDetailsDTO getResultDetails(Long resultId);

    Page<ResultPeekDTO> getPaginatedResults(Pageable pageable);

}
