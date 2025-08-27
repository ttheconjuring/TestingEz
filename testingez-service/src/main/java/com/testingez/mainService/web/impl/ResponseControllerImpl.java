package com.testingez.mainService.web.impl;

import com.testingez.mainService.models.dtos.imp.ResponseCreateDTO;
import com.testingez.mainService.services.ResponseService;
import com.testingez.mainService.web.ResponseController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/response")
public class ResponseControllerImpl implements ResponseController {

    private final ResponseService responseService;

    /*
     * This method simply saves user's response to question. Response is considered
     * a selected and submitted answer. It validates object, holding the response text
     * (the selected option text), the question id (so the response can be associated with
     * a one), test id and question number (so it can navigate to the next question, after
     * the response is saved). After response is inserted in the database, the users proceed
     * to the next question.
     */
    @Override
    @PostMapping("/save")
    public String save(@Valid ResponseCreateDTO responseData) {
        this.responseService.insert(responseData);
        return String.format("redirect:/questions/%d/%d",
                responseData.getTestId(), responseData.getQuestionNumber() + 1);
    }

}
