package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.services.CurrentUser;
import com.testingez.testingez.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("username", currentUser.getUsername());ø
        model.addAttribute("userProfileData", this.userService.getUserProfileData(this.currentUser.getId()));
        return "user-profile";
    }

    @GetMapping("/profile/edit")
    public String edit(Model model) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        model.addAttribute("username", currentUser.getUsername());
        if (!model.containsAttribute("userProfileData")) {
            model.addAttribute("userProfileData", this.userService.getUserProfileData(this.currentUser.getId()));
        }
        return "user-profile-edit";
    }

    @PostMapping("/profile/edit")
    public String edit(@Valid UserProfileDTO userProfileData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (!this.currentUser.isLogged()) {
            return "redirect:/account/login";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return "redirect:/user/profile/edit";
        }

        String result = this.userService.editProfileData(userProfileData, this.currentUser.getId());

        if (!result.equals("success")) {
            String errors = result.trim();
            if (errors.contains("username")) {
                redirectAttributes.addFlashAttribute("invalidUsername", true);
            }
            if (errors.contains("email")) {
                redirectAttributes.addFlashAttribute("invalidEmail", true);
            }
            if (errors.contains("phone")) {
                redirectAttributes.addFlashAttribute("invalidPhone", true);
            }
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return "redirect:/user/profile/edit";
        }

        redirectAttributes.addFlashAttribute("message", "profile updated successfully");
        return "redirect:/operation/success";
    }

}
