package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.services.CurrentUser;
import com.testingez.testingez.services.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {

    private final CurrentUser currentUser;
    private final TestService testService;

    @GetMapping("/join")
    public String join(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", currentUser.getUsername());
        }
        return "test-join";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", currentUser.getUsername());
        }
        if (!model.containsAttribute("testCreateDTO")) {
            model.addAttribute(new TestCreateDTO());
        }
        return "test-create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("testCreateData") TestCreateDTO testCreateDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.testCreateDTO", bindingResult);
            redirectAttributes.addFlashAttribute("testCreateDTO", testCreateDTO);
            return "redirect:/test/create";
        }
        this.testService.create(testCreateDTO);
        return "redirect:/";
    }

}
