package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

public interface FeedbackController {

    String postImprovement(ImprovementDTO improvementDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String checkImprovements(Model model);

    String deleteImprovement(UUID id);

}
