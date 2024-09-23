package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.QuestionEditDTO;
import com.testingez.testingez.models.dtos.imp.ResponseCreateDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.services.QuestionService;
import com.testingez.testingez.services.ResponseService;
import com.testingez.testingez.services.UserHelperService;
import com.testingez.testingez.web.QuestionsController;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/questions")
public class QuestionsControllerImpl implements QuestionsController {

    private final QuestionService questionService;
    private final UserHelperService userHelperService;
    private final ResponseService responseService;

    /*
     * This method leads to a page where a question with answers are given and the users
     * should select one and submit it. It also has a timer counting down to 0 that puts
     * limited time for response. The method accepts test id and question number to be able
     * to display the proper question and answers. First, the question with all the necessary
     * data is fetched. The object is null when such question is not found and this situation
     * is considered as test completion. Then, the users are redirected to page where their final
     * result is revealed. If such questions is found (not null), a method is invoked that makes sure
     * the users are not trying to cheat by reaching it twice, trying to answer again. If so, they are
     * automatically returned to the last accessed question. Lastly, if everything is alright, the
     * object holding the question is passed to the model and eventually displayed. Also, an empty
     * response object is passed, waiting for the users to bind some value to it.
     */
    @Override
    @GetMapping("/{testId}/{questionNumber}")
    public String answer(@PathVariable Long testId,
                         @PathVariable Integer questionNumber,
                         Model model,
                         HttpSession session) {
        QuestionAnswerDTO questionAnswerDTO = this.questionService.fetchQuestionData(testId, questionNumber);
        if (questionAnswerDTO == null) {
            return String.format("redirect:/results/%d/%d",
                    testId, this.userHelperService.getLoggedUser().getId());
        }
        if (this.responseService.isQuestionAnswered(testId, questionNumber)) {
            return String.format("redirect:/questions/%d/%d",
                    testId, questionNumber + 1);
        }

        // Fetch or set the start time when the user first sees the question
        LocalDateTime startTime = this.questionService.getOrSetStartTime(testId, questionNumber, session);

        model.addAttribute("questionData", questionAnswerDTO);
        model.addAttribute("responseData", new ResponseCreateDTO());
        model.addAttribute("startTime", startTime);
        return "question-answer";
    }

    /*
     * This method leads to a page where users can devise the questions for their test.
     * The method waits for test id, so the service logic can make the relations
     * between the test and the questions. To the model should be passed an object,
     * holding list of all questions put down so far. If the endpoint is hit manually,
     * an empty object is passed. If users are redirected to the endpoint with their object,
     * then it is passed to the model. This is the page where you write questions and eventually
     * complete the process of test creation. Also, if users are obstructed, they are able
     * to cancel (delete) their test by hitting the cancel button, placed at the bottom of the page.
     *
     */
    @Override
    @GetMapping("/{testId}/create")
    public String devise(@PathVariable Long testId, Model model) {
        if (!model.containsAttribute("testQuestionsData")) {
            model.addAttribute("testQuestionsData",
                    testQuestionsDTO(this.questionService.getQuestionsCountOfTheTest(testId), testId));
        }
        return "questions-create";
    }

    /*
     * This method receives object, holding questions the users have put down. The object,
     * along with all the questions are validated and if there are violations, users are
     * prompted to get back and fix their mistakes. A test id is required, so redirection
     * can be made and also to associate the questions with. After validation, the questions
     * are passed to method that saves them in the database and then redirects users to page
     * where they are informed that everything went fine.
     */
    @Override
    @PostMapping("/{testId}/create")
    public String save(@PathVariable Long testId,
                       @Valid TestQuestionsDTO testQuestionsData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testQuestionsData", bindingResult);
            redirectAttributes.addFlashAttribute("testQuestionsData", testQuestionsData);
            return String.format("redirect:/questions/%d/create", testId);
        }

        this.questionService.putDown(testQuestionsData, testId);

        redirectAttributes.addFlashAttribute("message", "You successfully created a test!");
        return "redirect:/operation/success";
    }

    /*
     * This method leads to a page where users can edit the questions of their own tests.
     * The method accepts question id, so it can find the proper one and display it. If
     * the endpoint is hit manually, the question data is fetched from the database. Otherwise,
     * the received object is passed to the model. This page allows users to overwrite a specific
     * question on their test. When the question is changed, users should click button "complete",
     * so the changes can get into the database.
     */
    @Override
    @GetMapping("/edit/{questionId}")
    public String edit(@PathVariable Long questionId, Model model) {
        if (!model.containsAttribute("questionData")) {
            model.addAttribute("questionData", this.questionService.fetchQuestionData(questionId));
        }
        return "question-edit";
    }

    /*
     * This is the method that gets the edited question data and tries to apply the update.
     * It should also receive the question id, in case something is not alright and should be
     * fixed (the users are redirected back). Once validated, the object, holding the edited
     * question, is given to a method that reflects the change. After successful update,
     * the users are redirected to the test details page, where they started the question
     * updating process.
     */
    @Override
    @PostMapping("/edit/{questionId}")
    public String edit(@PathVariable Long questionId,
                       @Valid QuestionEditDTO questionEditDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.questionData", bindingResult);
            redirectAttributes.addFlashAttribute("questionData", questionEditDTO);
            return String.format("redirect:/questions/edit/%d", questionId);
        }
        Boolean isEdited = this.questionService.edit(questionEditDTO);
        if (!isEdited) {
            redirectAttributes.addFlashAttribute("error", "attendanceError");
        } else {
            redirectAttributes.addFlashAttribute("success", "questionEdited");
        }
        return String.format("redirect:/test/details/%d", questionEditDTO.getTestId());
    }

    /*
     * This method leads to a page a new question can be created and attached to existing test.
     * In case we are redirected here for first time, we pass as an argument a new QuestionCreateDTO
     * to the model. We also pass the test id, so the question can be associated. A new question
     * is added only when the test is not attended.
     */
    @Override
    @GetMapping("/add/test/{testId}")
    public String add(@PathVariable Long testId, Model model) {
        if (!model.containsAttribute("questionData")) {
            model.addAttribute("questionData", new QuestionCreateDTO());
        }
        model.addAttribute("testId", testId);
        return "question-add";
    }

    /*
     * This method accepts filled QuestionCreateDTO and tries to add it to the test.
     * In case the incoming data is invalid, then the user gets back to correct the data.
     * If the question is added, the user is redirected to the test details where the new
     * question will appear. A new question is added only when the test is not attended.
     */
    @Override
    @PostMapping("/add/test/{testId}")
    public String add(@PathVariable Long testId,
                      @Valid QuestionCreateDTO questionData,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.questionData", bindingResult);
            redirectAttributes.addFlashAttribute("questionData", questionData);
            return String.format("redirect:/questions/add/test/%d", testId);
        }
        Boolean isAdded = this.questionService.add(testId, questionData);
        if (!isAdded) {
            redirectAttributes.addFlashAttribute("error", "attendanceError");
        } else {
            redirectAttributes.addFlashAttribute("success", "questionAdded");
        }
        return String.format("redirect:/test/details/%d", testId);
    }

    /*
     * This method accepts test id and question id and tries to
     * delete the question from the test. If the question is deleted,
     * then user is redirected to the test details page with green
     * alert saying "Changes applied!". If the question is not deleted,
     * then user is redirected to the test details page again, but this
     * time the alert is red, saying that the changes were not applied.
     */
    @Override
    @GetMapping("/delete/{testId}/{questionId}")
    public String delete(@PathVariable Long testId,
                         @PathVariable Long questionId,
                         RedirectAttributes redirectAttributes) {
        Boolean isDeleted = this.questionService.delete(questionId, testId);
        if (!isDeleted) {
            redirectAttributes.addFlashAttribute("error", "attendanceError");
        } else {
            redirectAttributes.addFlashAttribute("success", "questionDeleted");
        }
        return String.format("redirect:/test/details/%d", testId);
    }

    private TestQuestionsDTO testQuestionsDTO(int questionsCount, Long testId) {
        TestQuestionsDTO testQuestionsDTO = new TestQuestionsDTO();
        testQuestionsDTO.setTestId(testId);
        List<QuestionCreateDTO> questions = new ArrayList<>();
        for (int i = 0; i < questionsCount; i++) {
            questions.add(new QuestionCreateDTO());
        }
        testQuestionsDTO.setQuestions(questions);
        return testQuestionsDTO;
    }

}
