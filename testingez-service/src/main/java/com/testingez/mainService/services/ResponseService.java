package com.testingez.mainService.services;

import com.testingez.mainService.models.dtos.imp.ResponseCreateDTO;

public interface ResponseService {

    void insert(ResponseCreateDTO responseData);

    boolean isQuestionAnswered(Long testId, Integer questionNumber);
}
