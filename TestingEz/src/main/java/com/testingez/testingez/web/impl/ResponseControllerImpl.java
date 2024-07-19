package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.services.ResponseService;
import com.testingez.testingez.web.ResponseController;
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

    @Override
    @PostMapping("/save")
    public String save(@Valid ResponseCreateDTO responseData) {
        this.responseService.insert(responseData);
        return String.format("redirect:/questions/%d/%d",
                responseData.getTestId(), responseData.getQuestionNumber() + 1);
    }

}
