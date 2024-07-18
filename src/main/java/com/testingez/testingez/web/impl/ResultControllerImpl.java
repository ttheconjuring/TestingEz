package com.testingez.testingez.web.impl;

import com.testingez.testingez.web.ResultController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/results")
public class ResultControllerImpl implements ResultController {

    @Override
    @GetMapping("/{testId}/{userId}")
    public String result(@PathVariable Long testId,
                         @PathVariable Long userId) {
        System.out.println(testId);
        System.out.println(userId);
        return null;
    }

}
