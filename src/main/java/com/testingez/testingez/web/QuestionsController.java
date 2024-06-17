package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @GetMapping
    public String get(@RequestParam int questionsCount, Model model) {
        model.addAttribute("questionsCount", questionsCount);
        model.addAttribute("testQuestionsData", testQuestionsDTO(questionsCount));
        return "questions-create";
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
