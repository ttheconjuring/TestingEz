package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.services.QuestionService;
import com.testingez.testingez.web.QuestionsController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/questions")
public class QuestionsControllerImpl implements QuestionsController {

    private final QuestionService questionService;

    @Override
    @GetMapping("/{testId}/{questionNumber}")
    public String answer(@PathVariable Long testId,
                         @PathVariable Integer questionNumber,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        QuestionAnswerDTO questionAnswerDTO = this.questionService.fetchQuestionData(testId, questionNumber);
        if (questionAnswerDTO == null) {
            // TODO: redirect to result page
            redirectAttributes.addFlashAttribute("message", "You completed the test!");
            return "redirect:/operation/success";
        }
        model.addAttribute("questionData", questionAnswerDTO);
        model.addAttribute("responseData", new ResponseCreateDTO());
        return "question-answer";
    }

    @Override
    @GetMapping("/create")
    public String writeQuestions(Model model) {
        if (!model.containsAttribute("testQuestionsData")) {
            model.addAttribute("testQuestionsData",
                    testQuestionsDTO(this.questionService.getQuestionsCountOfTheLastAddedTest()));
        }
        return "questions-create";
    }

    @Override
    @PostMapping("/create")
    public String putDownQuestions(@Valid TestQuestionsDTO testQuestionsData,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testQuestionsData", bindingResult);
            redirectAttributes.addFlashAttribute("testQuestionsData", testQuestionsData);
            return "redirect:/questions/create";
        }

        boolean success = this.questionService.putDown(testQuestionsData);

        if (!success) {
            return null;
        }

        redirectAttributes.addFlashAttribute("message", "You successfully created a test!");
        return "redirect:/operation/success";
    }

    private TestQuestionsDTO testQuestionsDTO(int questionsCount) {
        TestQuestionsDTO testQuestionsDTO = new TestQuestionsDTO();
        List<QuestionCreateDTO> questions = new ArrayList<>();
        for (int i = 0; i < questionsCount; i++) {
            questions.add(new QuestionCreateDTO());
        }
        testQuestionsDTO.setQuestions(questions);
        return testQuestionsDTO;
    }

}
