package com.testingez.mainService.services.impls;

import com.testingez.mainService.exceptions.custom.ResultNotFoundException;
import com.testingez.mainService.exceptions.custom.TestNotFoundException;
import com.testingez.mainService.exceptions.custom.UserNotFoundException;
import com.testingez.mainService.models.dtos.exp.ResultPeekDTO;
import com.testingez.mainService.models.dtos.exp.ResultSummaryDTO;
import com.testingez.mainService.models.dtos.exp.ResultDetailsDTO;
import com.testingez.mainService.models.entities.Response;
import com.testingez.mainService.models.entities.Result;
import com.testingez.mainService.models.entities.Test;
import com.testingez.mainService.models.entities.User;
import com.testingez.mainService.models.enums.ResultStatus;
import com.testingez.mainService.repositories.*;
import com.testingez.mainService.services.ResultService;
import com.testingez.mainService.services.UserHelperService;
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

    /*
     * This method calculates the result of a user after the test is over. This method is
     * invoked after the last question is answered. It takes test id and user id and
     * tries to find them. If some of these two is missing, then an error is thrown.
     * Plain result object is created. First, the method calculates the total points
     * earned by iterating over the responses on the questions and filters only
     * the correct ones. At the same time, the method counts the correct answers,
     * so then a result can be set. The result shows correct answered questions/all questions.
     * Then, based on the total points earned, the result gets status. It is either PASS or FAIL,
     * and it is determined by the total points. If the points are equal or more than the passing
     * score of the test - the status is PASS, otherwise - FAIL. Finally, the result gets the
     * date and time when is created and also a test and user are attached to it. The result object
     * is saved in the database and then returned.
     */
    @Override
    public void calculateResult(Long testId, Long userId) {
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
        this.resultRepository.saveAndFlush(result);
    }

    /*
     * This method returns object holding small portion of the result itself.
     * It takes test id and user id, so it can find the correct result set of
     * correct test and the correct user. Once found, the result is mapped to
     * DTO and returned. If the result is not find for some reason, then an
     * error is thrown.
     */
    @Override
    public ResultSummaryDTO getResultSummary(Long testId, Long userId) {
        return this.modelMapper.map(
                this.resultRepository.findByTestIdAndUserId(testId, userId)
                        .orElseThrow(() ->
                                new ResultNotFoundException("We couldn't find result on test id: " + testId + " by user id: " + userId + "!")),
                ResultSummaryDTO.class);
    }

    /*
     * This method shows all information about specific result. Since this data is not
     * changeable, once found, the result is cached. The method accepts result id and
     * tries to find the result in the database. If it is not found, an error is thrown.
     * When the result is found, the object is mapped to DTO. Then the method is trying
     * to access the test of the result, so it can access the name of the test, which
     * is very useful information about the result. If the there is no test associated
     * with the result, an error is thrown.
     */
    @Cacheable(value = "result")
    @Override
    public ResultDetailsDTO getResultDetails(Long resultId) {
        LOGGER.info("Caching result details for: " + resultId);
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId));
        ResultDetailsDTO details = this.modelMapper.map(result, ResultDetailsDTO.class);
        Long testId = result.getTest().getId();
        details.setTestName(this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId)).getName());
        return details;
    }

    /*
     * This methods returns page with the results of the current logged user. It
     * gets all result object associated with the user and maps them to small DTO
     * that is exposed to the open world. The method gets the result test name,
     * so it is easier for the user to understand what result is he/she looking at.
     * If such test is not found, an error is thrown.
     */
    @Override
    public Page<ResultPeekDTO> getPaginatedResults(Pageable pageable) {
        Page<Result> results = this.resultRepository.findAllByUserId(this.userHelperService.getLoggedUser().getId(), pageable);
        return results.map(result -> {
            ResultPeekDTO resultPeekDTO = modelMapper.map(result, ResultPeekDTO.class);
            resultPeekDTO.setTestName(
                    this.testRepository.findById(result.getTest().getId())
                            .orElseThrow(() -> new TestNotFoundException("We couldn't " +
                                    "find test with id: " +
                                    result.getTest().getId())).getName());
            return resultPeekDTO;
        });
    }

}
