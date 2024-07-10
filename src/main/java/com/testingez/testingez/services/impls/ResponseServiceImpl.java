package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.ResponseRepository;
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

    @Override
    public void insert(ResponseCreateDTO responseData) {
        this.responseRepository.saveAndFlush(map(responseData));
    }

    private Response map(ResponseCreateDTO responseData) {
        Response response = new Response();
        Question question = this.questionRepository.findById(responseData.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question that should be associated with a response could not be found!"));
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
        return response;
    }


}
