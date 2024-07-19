package com.testingez.testingez.web.impl;

import com.testingez.testingez.services.ResultService;
import com.testingez.testingez.web.ResultController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/results")
public class ResultControllerImpl implements ResultController {

    private final ResultService resultService;

    @Override
    @GetMapping("/{testId}/{userId}")
    public String result(Model model,
                         @PathVariable Long testId,
                         @PathVariable Long userId) {
        model.addAttribute("resultData", this.resultService.getResult(testId, userId));
        return "show-result";
    }

}
