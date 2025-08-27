package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.TestJoinDTO;
import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.services.QuestionService;
import com.testingez.testingez.services.TestService;
import com.testingez.testingez.services.UserHelperService;
import com.testingez.testingez.web.TestController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class TestControllerImpl implements TestController {

    private final TestService testService;
    private final QuestionService questionService;
    private final UserHelperService userHelperService;

    @ModelAttribute("avatarUrl")
    public String avatarUrl() {
        return this.userHelperService.getLoggedUser().getAvatarUrl();
    }

    @ModelAttribute("testCreateData")
    public TestCreateDTO testCreateData() {
        return new TestCreateDTO();
    }

    @ModelAttribute("testJoinData")
    public TestJoinDTO testJoinData() {
        return new TestJoinDTO();
    }

    /*
     * This method leads users to page where they can enter test. An input field is
     * available for entering the code to join the test.
     */
    @Override
    @GetMapping("/join")
    public String join() {
        return "test-join";
    }

    /*
     * This method receives object that holds the code that the user has entered. The
     * object is validated to make sure the code it not empty or null. If so, the user
     * is notified by appropriate message. If the code is neither empty, nor null, it is
     * passed to a method that tries to find a test with such code associated.
     * There are 4 scenarios:
     *   1.Test is not found.
     *   2.Test is closed.
     *   3.Test is completed (user has attended)
     *   4.Test is open and not completed.
     * In the last case, users are redirected to test preview page.
     */
    @Override
    @PostMapping("/join")
    public String join(@Valid TestJoinDTO testJoinData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testJoinDTO", bindingResult);
            redirectAttributes.addFlashAttribute("testJoinData", testJoinData);
            return "redirect:/test/join";
        }
        String code = testJoinData.getCode();
        String status = this.testService.checkUponTest(code);
        return takeAction(status, code, testJoinData, redirectAttributes);
    }

    /*
     * This method leads users to page where a summary of the test is given. The method
     * accepts the code as request parameter, then passes it to a method that returns a
     * small peace of information about the test that is about to start. An object is received
     * and passed to the template. This is where user can actually start the test.
     */
    @Override
    @GetMapping("/join/preview")
    public String preview(@RequestParam("code") String code, Model model) {
        TestPreviewDTO testPreviewData = this.testService.getTestPreviewData(code);
        model.addAttribute("testPreviewData", testPreviewData);
        return "test-preview";
    }

    /*
     * This method leads to a page where users can create their own tests. This
     * page allows users to give a name to their test, description, time for response
     * on each question and some additional information that will be potentially exposed
     * to other people that are willing to join the test before they start it.
     */
    @Override
    @GetMapping("/create")
    public String create() {
        return "test-create";
    }

    /*
     * This method accepts the object that hold the test data, that the user has entered.
     * The object is validated and if there are some violations, the user is warned. If
     * everything is alright, the object is passed to a method that inserts the test into
     * the database and eventually returns the id of the test created. The id returned is
     * required for successful redirection to the next step in creating a test - the questions.
     * The questions should be associated with test id and that is why the endpoint contains
     * the test id: /questions/{id}/create.
     */
    @Override
    @PostMapping("/create")
    public String create(@Valid TestCreateDTO testCreateData,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testCreateData", bindingResult);
            redirectAttributes.addFlashAttribute("testCreateData", testCreateData);
            return "redirect:/test/create";
        }
        Long id = this.testService.create(testCreateData);
        return String.format("redirect:/questions/%d/create", id);
    }

    /*
     * This method receives an id of a test as path variable and tries to delete it.
     * Redirects to page that says the deletion was successful.
     */
    @Override
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        this.testService.delete(id);
        redirectAttributes.addFlashAttribute("message", "You deleted the test.");
        return "redirect:/operation/success";
    }

    /*
     * This method is only visible to ADMINISTRATOR. It reveals all the tests
     * in the system. This includes tests that are not created by the ADMINISTRATOR.
     */
    @Override
    @GetMapping("/all")
    public String all(Pageable pageable, Model model) {
        Page<TestPeekDTO> paginatedTests = this.testService.getAllPaginatedTests(pageable);
        model.addAttribute("paginatedTests", paginatedTests);
        return "all-tests";
    }

    /*
     * This method leads to a page, where the creator of the test can see the results
     * of his test. The leaderboard shows all contestants with their tests status
     * and actual result. If more curious, the creator can even check the answers
     * of anyone. The method waits for the test id, so it can find the result related
     * to that test and visualize them on the leaderboard.
     */
    @Override
    @GetMapping("/{id}/leaderboard")
    public String leaderboard(@PathVariable Long id, Model model) {
        model.addAttribute("testName", this.testService.getTestDetails(id).getName());
        model.addAttribute("userResults", this.testService.getTestLeaderboard(id));
        return "leaderboard";
    }

    /*
     * This method leads to a page with all the information about a test, including the
     * questions. It accepts id that is used to fetch all the required data. Two objects
     * are received then - test data and questions data. Both are passed to the template and
     * shown to the user. It also gives opportunity to edit the questions of the test.
     */
    @Override
    @GetMapping("/details/{id}")
    public String testDetails(@PathVariable Long id, Model model) {
        model.addAttribute("testDetails", this.testService.getTestDetails(id));
        model.addAttribute("testQuestions", this.questionService.getQuestionsOfATest(id));
        return "test-details";
    }

    /*
     * This method changes the current test status. If it is OPEN, it becomes CLOSED and vice versa.
     * The method depends on the path variable that holds the test id. After status is updated, users
     * are redirected back to the test details page.
     */
    @Override
    @GetMapping("/status/change/{id}")
    public String changeTestStatus(@PathVariable Long id) {
        this.testService.changeTestStatus(id);
        return String.format("redirect:/test/details/%d", id);
    }

    private String takeAction(String status, String code, TestJoinDTO testJoinData, RedirectAttributes redirectAttributes) {
        return switch (status) {
            case "not found" -> {
                redirectAttributes.addFlashAttribute("testNotFound", true);
                redirectAttributes.addFlashAttribute("testJoinData", testJoinData);
                yield "redirect:/test/join";
            }
            case "closed" -> {
                redirectAttributes.addFlashAttribute("testClosed", true);
                redirectAttributes.addFlashAttribute("testJoinData", testJoinData);
                yield "redirect:/test/join";
            }
            case "completed" -> {
                redirectAttributes.addFlashAttribute("testCompleted", true);
                redirectAttributes.addFlashAttribute("testJoinData", testJoinData);
                yield "redirect:/test/join";
            }
            case "ok" -> {
                redirectAttributes.addAttribute("code", code);
                yield "redirect:/test/join/preview";
            }
            default -> throw new IllegalStateException("Unexpected test status: " + status);
        };
    }

}
