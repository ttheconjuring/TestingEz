package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    Long create(TestCreateDTO testCreateDTO);

    void delete(Long id);

    String checkUponTest(String code);

    TestPreviewDTO getTestPreviewData(String code);

    Page<TestPeekDTO> getPaginatedTests(Pageable pageable);
}
