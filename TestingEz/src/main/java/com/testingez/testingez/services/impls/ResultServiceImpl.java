package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.exp.ResultDTO;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.enums.ResultStatus;
import com.testingez.testingez.repositories.ResponseRepository;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.repositories.UserRepository;
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
                .orElseThrow(() -> new RuntimeException("Test not found"));
        if (totalPoints >= test.getPassingScore()) {
            result.setStatus(ResultStatus.PASS);
        } else {
            result.setStatus(ResultStatus.FAIL);
        }
        result.setCompletedAt(LocalDateTime.now());
        result.setTest(test);
        result.setUser(this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found")));
        this.resultRepository.saveAndFlush(result);
    }

    @Override
    public ResultDTO getResult(Long testId, Long userId) {
        return this.modelMapper.map(
                this.resultRepository.findByTestIdAndUserId(testId, userId)
                        .orElseThrow(() ->
                                new NullPointerException("Result on testId: " + testId + " by userId: " + userId + " was not found!")),
                ResultDTO.class);
    }

}
