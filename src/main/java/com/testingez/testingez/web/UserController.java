package com.testingez.testingez.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/home")
    public String getUserHomeView(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "username");
        }
        return "test-join";
    }

}
