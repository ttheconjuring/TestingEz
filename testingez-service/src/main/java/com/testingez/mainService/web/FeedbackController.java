package com.testingez.mainService.web;

import com.testingez.mainService.exception.custom.NinjaMicroServiceException;
import com.testingez.mainService.model.dtos.ninja.FeedbackDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

public interface FeedbackController {

    String feedback(Model model);

    String post(FeedbackDTO improvementDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes)
            throws NinjaMicroServiceException;

    String check(Model model)
            throws NinjaMicroServiceException;

    String approve(UUID id)
            throws NinjaMicroServiceException;

    String disapprove(UUID id)
            throws NinjaMicroServiceException;

}
