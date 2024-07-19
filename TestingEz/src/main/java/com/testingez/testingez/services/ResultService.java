package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.ResultDTO;

public interface ResultService {

    void calculateResult(Long testId, Long userId);

    ResultDTO getResult(Long testId, Long userId);

}
