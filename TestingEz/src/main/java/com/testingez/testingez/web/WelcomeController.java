package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/feedback")
    public String contacts(ImprovementDTO improvementData, Model model) {
        model.addAttribute("improvementData", improvementData);
        return "feedback";
    }

}
