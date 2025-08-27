package com.testingez.mainService.web.impl;

import com.testingez.mainService.model.dtos.imp.UserSignInDTO;
import com.testingez.mainService.web.LoginController;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/account")
public class LoginControllerImpl implements LoginController {

    @ModelAttribute("userSignInData")
    public UserSignInDTO userSignInData() {
        return new UserSignInDTO();
    }

    /*
     * This method leads to a login page. Username and password are required in order
     * to sign in. A request param, saying "invalid credentials", is received, when
     * the users are entering invalid username or password.
     */
    @Override
    @GetMapping("/login")
    public String login(@RequestParam(required = false, name = "error") String error,
                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user/home";
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "sign-in";
    }

}
