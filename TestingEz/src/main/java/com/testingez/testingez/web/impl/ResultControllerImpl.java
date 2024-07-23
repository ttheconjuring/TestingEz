package com.testingez.testingez.web.impl;

import com.testingez.testingez.services.QuestionService;
import com.testingez.testingez.services.ResponseService;
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
    private final QuestionService questionService;
    private final ResponseService responseService;

    @Override
    @GetMapping("/{testId}/{userId}")
    public String resultSummary(Model model,
                                @PathVariable Long testId,
                                @PathVariable Long userId) {
        model.addAttribute("resultData", this.resultService.getResultSummary(testId, userId));
        return "show-result";
    }

    @Override
    @GetMapping("/details/{resultId}")
    public String resultDetails(@PathVariable Long resultId, Model model) {
        model.addAttribute("resultDetails", this.resultService.getResultDetails(resultId));
        model.addAttribute("answeredQuestions", this.questionService.getAnsweredQuestionsData(resultId));
        // model.addAttribute("responses", this.responseService.getResponses)
        return "result-details";
    }

}
