package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserSignInDataDTO;
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
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userSignInData")) {
            model.addAttribute("userSignInData", new UserSignInDataDTO());
        }
        return "sign-in";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("userSignInData") UserSignInDataDTO userSignInData,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        this.userService.login(userSignInData, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignInData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userSignInData", userSignInData);
            return "redirect:/account/login";
        }

        return "redirect:/user/home";
    }

}
