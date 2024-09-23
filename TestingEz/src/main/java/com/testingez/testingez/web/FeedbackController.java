package com.testingez.testingez.web;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

public interface FeedbackController {

    String feedback(Model model);

    String post(ImprovementDTO improvementDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes)
            throws NinjaMicroServiceException;

    String check(Model model)
            throws NinjaMicroServiceException;

    String disapprove(UUID id)
            throws NinjaMicroServiceException;

}
