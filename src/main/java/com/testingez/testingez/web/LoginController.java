package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
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

    /* @ModelAttribute("userSignIndata")
    public UserSignInDTO userSignInData() {
        return new UserSignInDTO();
    }

    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }
    */

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userSignInData")) {
            model.addAttribute("userSignInData", new UserSignInDTO());
        }
        return "sign-in";
    }

    @PostMapping("/login")
    public String login(@Valid UserSignInDTO userSignInData,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignInData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userSignInData", userSignInData);
            return "redirect:/account/login";
        }

        boolean success = this.userService.login(userSignInData);

        if (!success) {
            redirectAttributes.addFlashAttribute("invalidCredentials", true);
            redirectAttributes.addFlashAttribute("userSignInData", userSignInData);
            return "redirect:/account/login";
        }

        return "redirect:/user/home";
    }

}
