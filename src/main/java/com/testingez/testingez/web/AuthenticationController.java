package com.testingez.testingez.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
public class AuthenticationController {

    @GetMapping("/create")
    public String createAccount() {
        return "sign-up";
    }

    @GetMapping("/login")
    public String loginAccount() {
        return "sign-in";
    }

}
