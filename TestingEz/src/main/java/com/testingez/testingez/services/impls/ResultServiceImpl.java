package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.ResultNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.exceptions.custom.UserNotFoundException;
import com.testingez.testingez.models.dtos.exp.ResultPeekDTO;
import com.testingez.testingez.models.dtos.exp.ResultSummaryDTO;
import com.testingez.testingez.models.dtos.exp.ResultDetailsDTO;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.ResultStatus;
import com.testingez.testingez.repositories.*;
import com.testingez.testingez.services.ResultService;
import com.testingez.testingez.services.UserHelperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    private static final Logger LOGGER = Logger.getLogger(ResultServiceImpl.class.getName());

    @Override
    public Result calculateResult(Long testId, Long userId) {
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("We couldn't find user with id: " + userId));
        Result result = new Result();
        int totalPoints = 0;
        int correctAnswers = 0;
        List<Response> responses = this.responseRepository.findAllByTestIdAndUserId(testId, userId);
        for (Response response : responses) {
            if (response.getIsCorrect()) {
                correctAnswers++;
                totalPoints += response.getQuestion().getPoints();
            }
        }
        result.setPoints(totalPoints);
        result.setResult(correctAnswers + "/" + responses.size());
        if (totalPoints >= test.getPassingScore()) {
            result.setStatus(ResultStatus.PASS);
        } else {
            result.setStatus(ResultStatus.FAIL);
        }
        result.setCompletedAt(LocalDateTime.now());
        result.setTest(test);
        result.setUser(user);
        return this.resultRepository.saveAndFlush(result);
    }

    @Override
    public ResultSummaryDTO getResultSummary(Long testId, Long userId) {
        return this.modelMapper.map(
                this.resultRepository.findByTestIdAndUserId(testId, userId)
                        .orElseThrow(() ->
                                new ResultNotFoundException("We couldn't find result on test id: " + testId + " by user id: " + userId + "!")),
                ResultSummaryDTO.class);
    }

    @Cacheable(value = "result")
    @Override
    public ResultDetailsDTO getResultDetails(Long resultId) {
        LOGGER.info("Caching result details for: " + resultId);
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId + "!"));
        ResultDetailsDTO details = this.modelMapper.map(result, ResultDetailsDTO.class);
        Long testId = result.getTest().getId();
        details.setTestName(this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId + "!")).getName());
        return details;
    }

    @Override
    public Page<ResultPeekDTO> getPaginatedResults(Pageable pageable) {
        Page<Result> results = this.resultRepository.findAllByUserId(this.userHelperService.getLoggedUser().getId(), pageable);
        return results.map(result -> {
            ResultPeekDTO resultPeekDTO = modelMapper.map(result, ResultPeekDTO.class);
            resultPeekDTO.setTestName(
                    this.testRepository.findById(result.getTest().getId())
                            .orElseThrow(() -> new TestNotFoundException("We couldn't " +
                                    "find test with id: " +
                                    result.getTest().getId() + "!")).getName());
            return resultPeekDTO;
        });
    }

}
