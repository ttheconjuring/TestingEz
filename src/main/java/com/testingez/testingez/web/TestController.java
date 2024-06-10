package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.services.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class TestController {

    private final CurrentUser currentUser;

    @GetMapping("/join")
    public String getJoinTestView(Model model) {
        model.addAttribute("username", currentUser.getUsername());
        return "test-join";
    }

    @GetMapping("/create")
    public String getCreateTestView(Model model) {
        model.addAttribute("username", currentUser.getUsername());
        if (!model.containsAttribute("testCreateData")) {
            model.addAttribute(new TestCreateDTO());
        }
        return "test-create";
    }

}
