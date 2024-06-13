package com.testingez.testingez.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/questions")
public class QuestionsController {

    @GetMapping
    private String get(@RequestParam int questionsCount, Model model) {
        model.addAttribute("questionsCount", questionsCount);
        return "questions-create";
    }

}
