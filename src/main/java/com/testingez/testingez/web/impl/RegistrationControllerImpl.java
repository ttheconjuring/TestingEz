package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.services.UserService;
import com.testingez.testingez.web.RegistrationController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/account")
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @ModelAttribute("userSignUpData")
    public UserSignUpDTO userSignUpData() {
        return new UserSignUpDTO();
    }

    @Override
    @GetMapping("/create")
    public String register() {
        return "sign-up";
    }

    @Override
    @PostMapping("/create")
    public String register(@Valid UserSignUpDTO userSignUpData,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignUpData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userSignUpData", userSignUpData);
            return "redirect:/account/create";
        }

        String result = this.userService.register(userSignUpData);

        if (!result.equals("success")) {
            String errors = result.trim();
            if (errors.contains("username")) {
                redirectAttributes.addFlashAttribute("invalidUsername", true);
            }
            if (errors.contains("email")) {
                redirectAttributes.addFlashAttribute("invalidEmail", true);
            }
            if (errors.contains("phone")) {
                redirectAttributes.addFlashAttribute("invalidPhone", true);
            }
            if (errors.contains("passwords")) {
                redirectAttributes.addFlashAttribute("invalidPasswords", true);
            }
            redirectAttributes.addFlashAttribute("userSignUpData", userSignUpData);
            return "redirect:/account/create";
        }

        return "redirect:/user/home";
    }

}
