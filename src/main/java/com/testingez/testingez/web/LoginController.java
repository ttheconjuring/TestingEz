package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserLoginDataDTO;
import com.testingez.testingez.services.UserService;
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
    public String getLoginAccountView(Model model) {
        if (!model.containsAttribute("userLoginData")) {
            model.addAttribute("userLoginData", new UserLoginDataDTO());
        }
        return "sign-in";
    }

    @PostMapping("/login")
    public String postLoginAccount(@ModelAttribute("userLoginData") UserLoginDataDTO userLoginData,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        this.userService.login(userLoginData, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userLoginData", userLoginData);
            return "redirect:/account/login";
        }

        return "redirect:/user/home";
    }

}
