package com.testingez.mainService.service;

import com.testingez.mainService.model.dtos.imp.ResponseCreateDTO;

public interface ResponseService {

    void insert(ResponseCreateDTO responseData);

    boolean isQuestionAnswered(Long testId, Integer questionNumber);
}
