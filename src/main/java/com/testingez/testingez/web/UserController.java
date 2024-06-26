package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.services.CurrentUser;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final CurrentUser currentUser;
    private final UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        return "test-join";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("userProfileData", this.userService.getUserProfileData(this.currentUser.getId()));
        return "user-profile";
    }

    @GetMapping("/profile/edit")
    public String edit(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("userProfileData", this.userService.getUserProfileData(this.currentUser.getId()));
        return "user-profile-edit";
    }

    @PostMapping("/profile/edit")
    public String edit() {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        return "";
    }

}
