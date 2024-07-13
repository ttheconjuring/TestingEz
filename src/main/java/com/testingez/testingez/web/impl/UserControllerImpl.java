package com.testingez.testingez.web.impl;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.services.NinjaService;
import com.testingez.testingez.services.UserService;
import com.testingez.testingez.web.UserController;
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
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final NinjaService ninjaService;

    @Override
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("testOfTheDay", this.ninjaService.fetchTrivia());
        model.addAttribute("factsOfTheDay", this.ninjaService.fetchFacts());
        model.addAttribute("jokesOfTheDay", this.ninjaService.fetchJokes());
        model.addAttribute("quotesOfTheDay", this.ninjaService.fetchQuotes());
        return "user-home";
    }

    @Override
    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("userProfileData", this.userService.getUserProfileData());
        return "user-profile";
    }

    @Override
    @GetMapping("/profile/edit")
    public String edit(Model model) {
        if (!model.containsAttribute("userProfileData")) {
            model.addAttribute("userProfileData", this.userService.getUserProfileData());
        }
        return "user-profile-edit";
    }

    @Override
    @PostMapping("/profile/edit")
    public String edit(@Valid UserProfileDTO userProfileData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return "redirect:/user/profile/edit";
        }

        String result = this.userService.editProfileData(userProfileData);

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
