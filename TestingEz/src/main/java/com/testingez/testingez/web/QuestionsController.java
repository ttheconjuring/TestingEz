package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.QuestionEditDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface QuestionsController {

    String answerQuestion(Long testId, Integer questionNumber, Model model, RedirectAttributes redirectAttributes);

    String deviseQuestions(Long testId, Model model);

    String saveQuestionsToDb(Long testId, TestQuestionsDTO testQuestionsData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String edit(Long questionId, Model model);

    String edit(Long questionId, QuestionEditDTO questionAnswerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String add(Long testId, Model model);

    String add(Long testId, QuestionCreateDTO questionCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
