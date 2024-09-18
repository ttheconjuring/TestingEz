package com.testingez.testingez.web;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

public interface UserController {

    String home(Model mode) throws NinjaMicroServiceException;

    String profile(Model model);

    String edit(Model model);

    String edit(UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String userProfile(Long id, Model model);

    String userEdit(Long id, Model model);

    String userEdit(Long id, UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String delete(Model model);

    String delete(Long id);

    String avatar();

    String all(Pageable pageable, Model model);

    String tests(Pageable pageable, Model model);

    String results(Pageable pageable, Model model);

    String postImprovement(ImprovementDTO improvementDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String checkImprovements(Model model);

    String deleteImprovement(UUID id);

}
