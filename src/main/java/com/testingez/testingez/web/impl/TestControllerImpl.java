package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.TestJoinDTO;
import com.testingez.testingez.models.dtos.TestStartDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.services.TestService;
import com.testingez.testingez.web.TestController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @ModelAttribute("testCreateData")
    public TestCreateDTO testCreateData() {
        return new TestCreateDTO();
    }

    @ModelAttribute("testJoinData")
    public TestJoinDTO testJoinData() {
        return new TestJoinDTO();
    }

    @Override
    @GetMapping("/join")
    public String join() {
        return "test-join";
    }

    @Override
    @PostMapping("/join")
    public String join(@Valid TestJoinDTO testJoinData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("testJoinData", testJoinData);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testJoinDTO", bindingResult);
            return "redirect:/test/join";
        }
        String result = this.testService.checkUponTest(testJoinData.getCode());
        if (result.equals("not found")) {
            redirectAttributes.addFlashAttribute("testNotFound", true);
            return "redirect:/test/join";
        }
        if (result.equals("closed")) {
            redirectAttributes.addFlashAttribute("testClosed", true);
            return "redirect:/test/join";
        }
        redirectAttributes.addAttribute("code", testJoinData.getCode());
        return "redirect:/test/join/preview";
    }

    @Override
    @GetMapping("/join/preview")
    public String preview(@RequestParam("code") String code, Model model) {
        TestPreviewDTO testPreviewData = this.testService.getTestPreviewData(code);
        model.addAttribute("testPreviewData", testPreviewData);
        return "test-preview";
    }

    @Override
    @PostMapping("/join/preview")
    public String preview(@Valid TestStartDTO testStartData,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // add custom exception handle for invalid test id
            return null;
        }
        return String.format("redirect:/questions/%d/%d", testStartData.getId(), 1);
    }

    @Override
    @GetMapping("/create")
    public String create() {
        return "test-create";
    }

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
        this.testService.create(testCreateData);
        return "redirect:/questions/create";
    }

    @Override
    @PostMapping("/delete")
    public String delete(@RequestParam(required = false, defaultValue = "-1") Long id,
                         RedirectAttributes redirectAttributes) {
        this.testService.delete(id);
        redirectAttributes.addFlashAttribute("message", "You cancelled the test.");
        return "redirect:/operation/success";
    }
}
