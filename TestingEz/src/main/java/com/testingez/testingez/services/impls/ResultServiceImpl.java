package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.ResultNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.exceptions.custom.UserNotFoundException;
import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.ResultSummaryDTO;
import com.testingez.testingez.models.dtos.exp.ResultDetailsDTO;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.enums.ResultStatus;
import com.testingez.testingez.repositories.*;
import com.testingez.testingez.services.ResultService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ResponseRepository responseRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final QuestionRepository questionRepository;

    @Override
    public void calculateResult(Long testId, Long userId) {
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
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId + "!"));
        if (totalPoints >= test.getPassingScore()) {
            result.setStatus(ResultStatus.PASS);
        } else {
            result.setStatus(ResultStatus.FAIL);
        }
        result.setCompletedAt(LocalDateTime.now());
        result.setTest(test);
        result.setUser(this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("We couldn't find user with id: " + userId + "!")));
        this.resultRepository.saveAndFlush(result);
    }

    @Override
    public ResultSummaryDTO getResultSummary(Long testId, Long userId) {
        return this.modelMapper.map(
                this.resultRepository.findByTestIdAndUserId(testId, userId)
                        .orElseThrow(() ->
                                new ResultNotFoundException("We couldn't find result on test id: " + testId + " by user id: " + userId + "!")),
                ResultSummaryDTO.class);
    }

    @Override
    public ResultDetailsDTO getResultDetails(Long resultId) {
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId + "!"));
        ResultDetailsDTO details = this.modelMapper.map(result, ResultDetailsDTO.class);
        Long testId = result.getTest().getId();
        details.setTestName(this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId + "!")).getName());
        return details;
    }

    @Override
    public List<AnsweredQuestionDTO> getAnsweredQuestionsData(Long resultId) {
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId + "!"));
        Long testId = result.getTest().getId();
        return this.questionRepository.findAllByTestId(testId)
                .stream()
                .map(question -> this.modelMapper.map(question, AnsweredQuestionDTO.class))
                .toList();
    }

}
