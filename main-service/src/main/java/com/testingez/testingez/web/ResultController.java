package com.testingez.testingez.web;

import org.springframework.ui.Model;

public interface ResultController {

    String resultSummary(Model model, Long testId, Long userId);

    String resultDetails(Long resultId, Model model);

}
