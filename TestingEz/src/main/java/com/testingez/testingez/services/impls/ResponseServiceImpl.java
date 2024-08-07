package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.QuestionNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.ResponseRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.ResponseService;
import com.testingez.testingez.services.UserHelperService;
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
