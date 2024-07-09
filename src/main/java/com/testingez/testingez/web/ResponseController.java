package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/response")
public class ResponseController {

    @PostMapping(value = "/save")
    public String saveResponse(@Valid ResponseCreateDTO responseData) {
        // TODO: handle the response
        return String.format("redirect:/questions/%d/%d",
                responseData.getTestId(), responseData.getQuestionNumber() + 1);
    }

}
