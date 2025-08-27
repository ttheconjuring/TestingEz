package com.testingez.mainService.service.impls;

import com.testingez.mainService.exception.custom.QuestionNotFoundException;
import com.testingez.mainService.exception.custom.TestNotFoundException;
import com.testingez.mainService.model.dtos.imp.ResponseCreateDTO;
import com.testingez.mainService.model.entities.Question;
import com.testingez.mainService.model.entities.Response;
import com.testingez.mainService.repository.QuestionRepository;
import com.testingez.mainService.repository.ResponseRepository;
import com.testingez.mainService.repository.TestRepository;
import com.testingez.mainService.service.ResponseService;
import com.testingez.mainService.service.UserHelperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final UserHelperService userHelperService;
    private final TestRepository testRepository;

    @Override
    public void insert(ResponseCreateDTO responseData) {
        this.responseRepository.saveAndFlush(map(responseData));
    }

    @Override
    public boolean isQuestionAnswered(Long testId, Integer questionNumber) {
        return this.responseRepository.findByTestIdAndQuestionIdAndUserId(testId,
                this.questionRepository.findByTestIdAndNumber(testId, questionNumber)
                        .orElseThrow(() -> new QuestionNotFoundException("We couldn't " +
                                "find question №" + questionNumber + " associated " +
                                "with test id:" + testId + " to see if it is " +
                                "answered or not!")).getId(),
                this.userHelperService.getLoggedUser().getId()).isPresent();
    }

    private Response map(ResponseCreateDTO responseData) {
        Response response = new Response();
        Question question = this.questionRepository.findById(responseData.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("We couldn't find question with id: " + responseData.getQuestionId() + "!"));
        String responseText = responseData.getResponseText();
        if (responseText == null) {
            response.setResponseText("(no selected answer)");
            response.setIsCorrect(false);
        } else {
            response.setResponseText(responseText);
            response.setIsCorrect(responseText.equals(question.getCorrectAnswer()));
        }
        response.setSubmittedOn(LocalDateTime.now());
        response.setUser(this.userHelperService.getLoggedUser());
        response.setQuestion(question);
        response.setTest(this.testRepository.findById(responseData.getTestId())
                .orElseThrow(() ->
                        new TestNotFoundException("We couldn't find test with id: " + responseData.getTestId() + "!")));
        return response;
    }

}
