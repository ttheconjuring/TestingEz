package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.services.UserService;
import com.testingez.testingez.web.RegistrationController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/account")
public class RegistrationControllerImpl implements RegistrationController {

    private final UserService userService;

    @ModelAttribute("userSignUpData")
    public UserSignUpDTO userSignUpData() {
        return new UserSignUpDTO();
    }

    /*
     * This method leads to a page where users can create their accounts. The
     * registration process is simple and straightforward. Users are prompted
     * to give their profiles a wonderful usernames, some other stuff and leave
     * some contacts.
     */
    @Override
    @GetMapping("/create")
    public String register() {
        return "sign-up";
    }

    /*
     * This method holds the object with the registration data. The data should be valid,
     * otherwise the users are not allowed to create account. Some error messages are shown.
     * Once the object is valid and the data meets the criteria, it is passed to another method
     * that tries to create the account. There are 5 scenarios:
     *   1.The user tries to use occupied username. (an error occurs)
     *   2.The user tries to use occupied email. (an error occurs)
     *   3.The user tries to use occupied phone. (an error occurs)
     *   4.Password and confirm password don't match. (an error occurs)
     *   5.Everything is alright.
     * If the last case is hit, account is created and users are redirected to
     * login page.
     */
    @Override
    @PostMapping("/create")
    public String register(@Valid UserSignUpDTO userSignUpData,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userSignUpData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userSignUpData", userSignUpData);
            return "redirect:/account/create";
        }

        String result = this.userService.register(userSignUpData);

        if (!result.equals("success")) {
            handleRegistrationErrors(result, redirectAttributes, userSignUpData);
            return "redirect:/account/create";
        }

        return "redirect:/account/login";
    }

    private void handleRegistrationErrors(String result,
                                          RedirectAttributes redirectAttributes,
                                          UserSignUpDTO userSignUpData) {
        if (result.contains("username")) {
            redirectAttributes.addFlashAttribute("invalidUsername", true);
        }
        if (result.contains("email")) {
            redirectAttributes.addFlashAttribute("invalidEmail", true);
        }
        if (result.contains("phone")) {
            redirectAttributes.addFlashAttribute("invalidPhone", true);
        }
        if (result.contains("passwords")) {
            redirectAttributes.addFlashAttribute("invalidPasswords", true);
        }
        redirectAttributes.addFlashAttribute("userSignUpData", userSignUpData);
    }

}
