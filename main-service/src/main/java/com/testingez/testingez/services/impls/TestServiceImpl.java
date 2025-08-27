package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.TestDetailsDTO;
import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.exp.UserResultDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.Result;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;
    private final ResultRepository resultRepository;

    /*
     * This method accepts object holding the most important
     * data for an account to be created. The dto is then mapped
     * to entity. Date and time are added for creation and last
     * update. The test is associated with the current logged user.
     * Finally, the test is saved in the database and the id is
     * returned.
     */
    @Override
    public Long create(TestCreateDTO testCreateDTO) {
        Test newTest = modelMapper.map(testCreateDTO, Test.class);
        newTest.setStatus(testCreateDTO.getStatus().equals("open") ? TestStatus.OPEN : TestStatus.CLOSED);
        newTest.setDateCreated(LocalDateTime.now());
        newTest.setDateUpdated(LocalDateTime.now());
        newTest.setCreator(this.userHelperService.getLoggedUser());
        return this.testRepository.saveAndFlush(newTest).getId();
    }

    /*
     * This method simply delete the test with the given id.
     * If the id given is invalid, then nothing happens.
     */
    @Override
    public void delete(Long id) {
        this.testRepository.deleteById(id);
    }


    /*
     * This method accepts the code and tries to find it. Then,
     * if found, it checks if the test status is 'OPEN'. After that,
     * it tries to see if the user has already attended this test before.
     * Finally, it a string saying 'ok' is returned. If the code is invalid
     * or null, a string saying 'not found' is returned. If the test code
     * is 'CLOSED', a string saying 'closed' is returned. If the user
     * has entered the test yet, a string saying 'completed' is returned.
     */
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

    /*
     * This method accepts test code and tries to make a test preview dto.
     * If the code is valid and the test is found, it is mapped to dto and
     * returned. If not, an error is thrown.
     */
    @Override
    public TestPreviewDTO getTestPreviewData(String code) {
        return this.modelMapper.map(
                this.testRepository.findByCode(code)
                        .orElseThrow(() ->
                                new TestNotFoundException("We couldn't find test with code: " + code)),
                TestPreviewDTO.class);
    }


    /*
     * This method gets all tests from the database and maps them
     * to small peek dto. If there are no tests, then an empty page
     * is returned.
     */
    @Override
    public Page<TestPeekDTO> getAllPaginatedTests(Pageable pageable) {
        Page<Test> tests = this.testRepository.findAll(pageable);
        return tests.map(test -> modelMapper.map(test, TestPeekDTO.class));
    }


    /*
     * This method gets test id and tries to retrieve detailed
     * information about the test. If test with such id doesn't
     * exist or is not found, then an error is thrown.
     */
    @Override
    public TestDetailsDTO getTestDetails(Long testId) {
        return this.modelMapper.map(this.testRepository.findById(testId)
                        .orElseThrow(
                                () -> new TestNotFoundException("We couldn't find test with id: " + testId)),
                TestDetailsDTO.class);
    }

    /*
     * This method will rely on test id in order to change it status. If
     * the test is open, then it becomes closed and vice versa. The date
     * and time when the test is updated are also saved in the database.
     * If the id is invalid, then an error is thrown.
     */
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

    /*
     * This method finds all tests created by the current logged user, maps each test to DTO and then
     * returns the pageable object holding the data.
     */
    @Override
    public Page<TestPeekDTO> getSomePaginatedTests(Pageable pageable) {
        Page<Test> tests = this.testRepository.findAllByCreatorId(this.userHelperService.getLoggedUser().getId(), pageable);
        return tests.map(test -> modelMapper.map(test, TestPeekDTO.class));
    }

    /*
     * This method tries to find all results related to the given test id.
     * Once found, the results are already sorted by points in descending order,
     * they are iterated over and every result is mapped to UserResultDTO - object,
     * containing mixed properties out of User and Result entity objects. All these
     * mapped object are collected to list and the list is returned.
     */
    @Override
    public List<UserResultDTO> getTestLeaderboard(Long id) {
        return this.resultRepository.findAllByTestIdOrderByPointsDesc(id)
                .stream()
                .map(this::mapResultToThinResult)
                .collect(Collectors.toList());
    }

    private UserResultDTO mapResultToThinResult(Result result) {
        UserResultDTO userResultDTO = new UserResultDTO();
        userResultDTO.setId(result.getId());
        userResultDTO.setAvatarUrl(result.getUser().getAvatarUrl());
        userResultDTO.setUsername(result.getUser().getUsername());
        userResultDTO.setPoints(result.getPoints());
        userResultDTO.setResult(result.getResult());
        userResultDTO.setStatus(result.getStatus().name());
        return userResultDTO;
    }


}
