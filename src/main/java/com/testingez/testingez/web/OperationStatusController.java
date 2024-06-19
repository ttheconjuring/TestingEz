package com.testingez.testingez.web;

import com.testingez.testingez.services.CurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/operation")
public class OperationStatusController {

    private final CurrentUser currentUser;

    @GetMapping("/success")
    public String success() {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        return "success";
    }

}
