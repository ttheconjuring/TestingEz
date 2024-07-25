package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface QuestionsController {

    String answer(Long testId, Integer questionNumber, Model model, RedirectAttributes redirectAttributes);

    String writeQuestions(Long testId, Model model);

    String putDownQuestions(Long testId, TestQuestionsDTO testQuestionsData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String edit(Long questionId, Model model);

}
