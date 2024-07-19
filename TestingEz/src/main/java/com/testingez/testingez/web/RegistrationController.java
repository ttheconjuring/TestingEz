package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface RegistrationController {

    String register();

    String register(UserSignUpDTO userSignUpData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
