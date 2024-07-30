package com.testingez.testingez.web;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserController {

    String home(Model mode) throws NinjaMicroServiceException;

    String profile(Model model);

    String edit(Model model);

    String edit(UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String userTests(Pageable pageable, Model model);

    String userResults(Pageable pageable, Model model);

    String postImprovement(ImprovementDTO improvementDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String checkImprovements(Model model);

}
