package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.ResultPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestDetailsDTO;
import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.enums.TestStatus;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.TestService;
import com.testingez.testingez.services.UserHelperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final ResultRepository resultRepository;

    @Override
    public Long create(TestCreateDTO testCreateDTO) {
        Test newTest = modelMapper.map(testCreateDTO, Test.class);
        newTest.setStatus(testCreateDTO.getStatus().equals("open") ? TestStatus.OPEN : TestStatus.CLOSED);
        newTest.setDateCreated(LocalDateTime.now());
        newTest.setDateUpdated(LocalDateTime.now());
        newTest.setCreator(this.userHelperService.getLoggedUser());
        return this.testRepository.saveAndFlush(newTest).getId();
    }

    @Override
    public void delete(Long id) {
        this.testRepository.deleteById(id);
    }

    @Override
    public String checkUponTest(String code) {
        Optional<Test> byCode = this.testRepository.findByCode(code);
        if (byCode.isEmpty()) {
            return "not found";
        }
        Test test = byCode.get();
        if (test.getStatus().equals(TestStatus.CLOSED)) {
            return "closed";
        }
        if (this.resultRepository
                .findByTestIdAndUserId(test.getId(),
                        this.userHelperService.getLoggedUser().getId())
                .isPresent()) {
            return "completed";
        }
        return "ok";
    }

    @Override
    public TestPreviewDTO getTestPreviewData(String code) {
        return this.modelMapper.map(
                this.testRepository.findByCode(code)
                        .orElseThrow(() ->
                                new TestNotFoundException("We couldn't find test with code: " + code + "!")),
                TestPreviewDTO.class);
    }

    @Override
    public Page<TestPeekDTO> getPaginatedTests(Pageable pageable) {
        Page<Test> tests = this.testRepository.findAll(pageable);
        return tests.map(test -> modelMapper.map(test, TestPeekDTO.class));
    }

    @Override
    public TestDetailsDTO getTestDetails(Long testId) {
        return this.modelMapper.map(this.testRepository.findById(testId)
                        .orElseThrow(
                                () -> new TestNotFoundException("We couldn't find test with id: " + testId)),
                TestDetailsDTO.class);
    }

    @Override
    public void changeTestStatus(Long id) {
        Test test = this.testRepository.findById(id)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + id));
        if (test.getStatus().equals(TestStatus.CLOSED)) {
            test.setStatus(TestStatus.OPEN);
        } else {
            test.setStatus(TestStatus.CLOSED);
        }
        test.setDateUpdated(LocalDateTime.now());
        this.testRepository.saveAndFlush(test);
    }

}
