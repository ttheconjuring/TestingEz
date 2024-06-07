package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import com.testingez.testingez.services.UserService;
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
@RequestMapping("/account")
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/create")
    public String getCreateAccountView(Model model) {
        if (!model.containsAttribute("userSignUpData")) {
            model.addAttribute("userSignUpData", new UserSignUpDataDTO());
        }
        return "sign-up";
    }

    @PostMapping("/create")
    public String postCreateAccount(@Valid @ModelAttribute("userSignUpData") UserSignUpDataDTO userSignUpData,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        this.userService.confirmPasswords(userSignUpData, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignUpData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userSignUpData", userSignUpData);
            return "redirect:/account/create";
        }

        this.userService.register(userSignUpData);

        redirectAttributes.addFlashAttribute("username", userSignUpData.getUsername());

        return "redirect:/user/home";
    }

}
