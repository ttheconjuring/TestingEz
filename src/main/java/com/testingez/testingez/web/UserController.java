package com.testingez.testingez.web;

import com.testingez.testingez.services.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final CurrentUser currentUser;

    @GetMapping("/home")
    public String getUserHomeView(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        return "test-join";
    }

}
