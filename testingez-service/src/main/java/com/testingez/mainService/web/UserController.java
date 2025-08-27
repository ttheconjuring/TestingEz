package com.testingez.mainService.web;

import com.testingez.mainService.exceptions.custom.NinjaMicroServiceException;
import com.testingez.mainService.models.dtos.UserProfileDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserController {

    String home(Model mode) throws NinjaMicroServiceException;

    String profile(Model model);

    String edit(Model model);

    String edit(UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String userProfile(Long id, Model model);

    String userEdit(Long id, Model model);

    String userEdit(Long id, UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String delete(Long id, Model model);

    String delete(Long id, HttpServletRequest request, HttpServletResponse response);

    String avatar();

    String all(Pageable pageable, Model model);

    String tests(Pageable pageable, Model model);

    String results(Pageable pageable, Model model);

}
