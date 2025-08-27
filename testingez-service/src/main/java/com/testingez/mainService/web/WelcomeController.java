package com.testingez.mainService.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/FAQs")
    public String FAQs() {
        return "faqs";
    }

    @GetMapping("/features")
    public String features() {
        return "features";
    }

}
