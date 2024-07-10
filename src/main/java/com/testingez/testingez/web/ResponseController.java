package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.services.ResponseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/response")
public class ResponseController {

    private final ResponseService responseService;

    @PostMapping("/save")
    public String saveResponse(@Valid ResponseCreateDTO responseData) {
        this.responseService.insert(responseData);
        return String.format("redirect:/questions/%d/%d",
                responseData.getTestId(), responseData.getQuestionNumber() + 1);
    }

}
