package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/account")
public class LoginController {

    @ModelAttribute("userSignInData")
    public UserSignInDTO userSignInData() {
        return new UserSignInDTO();
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false, name = "error") String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "sign-in";
    }

}
