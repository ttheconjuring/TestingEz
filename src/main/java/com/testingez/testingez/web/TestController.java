package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.services.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @ModelAttribute("testCreateData")
    public TestCreateDTO testCreateData() {
        return new TestCreateDTO();
    }

    @ModelAttribute("username")
    public String username(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @GetMapping("/join")
    public String join() {
        return "test-join";
    }

    @GetMapping("/create")
    public String create() {
        return "test-create";
    }

    @PostMapping("/create")
    public String create(@AuthenticationPrincipal UserDetails userDetails,
                         @Valid TestCreateDTO testCreateData,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testCreateData", bindingResult);
            redirectAttributes.addFlashAttribute("testCreateData", testCreateData);
            return "redirect:/test/create";
        }
        this.testService.create(testCreateData, userDetails.getUsername());
        return "redirect:/questions?questionsCount=" + testCreateData.getQuestionsCount();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(required = false, defaultValue = "-1") Long id, RedirectAttributes redirectAttributes) {
        this.testService.delete(id);
        redirectAttributes.addFlashAttribute("message", "You cancelled the test.");
        return "redirect:/operation/success";
    }
}
