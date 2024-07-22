package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;

public interface TestService {

    Long create(TestCreateDTO testCreateDTO);

    void delete(Long id);

    String checkUponTest(String code);

    TestPreviewDTO getTestPreviewData(String code);
}
