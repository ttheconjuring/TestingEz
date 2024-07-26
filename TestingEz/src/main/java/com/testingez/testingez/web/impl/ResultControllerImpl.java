package com.testingez.testingez.web.impl;

import com.testingez.testingez.services.QuestionService;
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

    /*
     * This method leads to a page that is shown after a test is completed. The page
     * gives information about the performance of the user, including the result, the points
     * and status if the passed it or failed it. The method should receive both the test id
     * and the user id, in order to find the proper result. The ids are passed to another method that
     * retrieves the result summary object. It is passed to the model and displayed. Once reached,
     * the page allows users to get back to the home page.
     */
    @Override
    @GetMapping("/{testId}/{userId}")
    public String resultSummary(Model model,
                                @PathVariable Long testId,
                                @PathVariable Long userId) {
        model.addAttribute("resultData", this.resultService.getResultSummary(testId, userId));
        return "show-result";
    }

    /*
     * This method leads to a page that contains detailed information about the result and the performance
     * of the user. It accepts the result id that is passed to two methods: the first one fetches data about
     * the result itself and the second one fetches the data about how the user has answered every question.
     * Both objects are passed to the template. Here, users can see the correct answers on the test and their
     * own answer on each question.
     */
    @Override
    @GetMapping("/details/{resultId}")
    public String resultDetails(@PathVariable Long resultId, Model model) {
        model.addAttribute("resultDetails", this.resultService.getResultDetails(resultId));
        model.addAttribute("answeredQuestions", this.questionService.getAnsweredQuestionsData(resultId));
        return "result-details";
    }

}
