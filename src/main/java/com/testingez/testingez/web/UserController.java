package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserController {

    String home(Model mode);

    String profile(Model model);

    String edit(Model model);

    String edit(UserProfileDTO userProfileData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String userTests(Pageable pageable, Model model);

}
