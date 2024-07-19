package com.testingez.testingez.web;

import org.springframework.ui.Model;

public interface ResultController {

    String result(Model model, Long testId, Long userId);

}
