package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "test-join";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails,
                          Model model) {
        model.addAttribute("userProfileData", this.userService.getUserProfileData(userDetails.getUsername()));
        return "user-profile";
    }

    @GetMapping("/profile/edit")
    public String edit(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {
        if (!model.containsAttribute("userProfileData")) {
            model.addAttribute("userProfileData", this.userService.getUserProfileData(userDetails.getUsername()));
        }
        return "user-profile-edit";
    }

    @PostMapping("/profile/edit")
    public String edit(@AuthenticationPrincipal UserDetails userDetails,
                       @Valid UserProfileDTO userProfileData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return "redirect:/user/profile/edit";
        }

        String result = this.userService.editProfileData(userProfileData, userDetails.getUsername());

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
