package com.testingez.mainService.service;

import com.testingez.mainService.model.dtos.exp.ResultPeekDTO;
import com.testingez.mainService.model.dtos.exp.ResultSummaryDTO;
import com.testingez.mainService.model.dtos.exp.ResultDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResultService {

    void calculateResult(Long testId, Long userId);

    ResultSummaryDTO getResultSummary(Long testId, Long userId);

    ResultDetailsDTO getResultDetails(Long resultId);

    Page<ResultPeekDTO> getPaginatedResults(Pageable pageable);

}
