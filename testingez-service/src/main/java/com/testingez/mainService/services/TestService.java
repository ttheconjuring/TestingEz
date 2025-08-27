package com.testingez.mainService.services;

import com.testingez.mainService.models.dtos.exp.TestDetailsDTO;
import com.testingez.mainService.models.dtos.exp.TestPeekDTO;
import com.testingez.mainService.models.dtos.exp.TestPreviewDTO;
import com.testingez.mainService.models.dtos.exp.UserResultDTO;
import com.testingez.mainService.models.dtos.imp.TestCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestService {

    Long create(TestCreateDTO testCreateDTO);

    void delete(Long id);

    String checkUponTest(String code);

    TestPreviewDTO getTestPreviewData(String code);

    Page<TestPeekDTO> getAllPaginatedTests(Pageable pageable);

    TestDetailsDTO getTestDetails(Long testId);

    void changeTestStatus(Long id);

    Page<TestPeekDTO> getSomePaginatedTests(Pageable pageable);

    List<UserResultDTO> getTestLeaderboard(Long id);
}
