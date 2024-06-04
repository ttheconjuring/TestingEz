package com.testingez.testingez.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/join")
    public String getJoinTestView(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "username");
        }
        return "test-join";
    }

    @GetMapping("/create")
    public String getCreateTestView(Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", "username");
        }
        return "test-create";
    }

}
