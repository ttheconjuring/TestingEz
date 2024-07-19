package com.testingez.testingez.web.impl;

import com.testingez.testingez.web.OperationStatusController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/operation")
public class OperationStatusControllerImpl implements OperationStatusController {

    @Override
    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @Override
    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }

}
