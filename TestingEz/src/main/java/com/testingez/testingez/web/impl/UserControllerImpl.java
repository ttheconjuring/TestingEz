package com.testingez.testingez.web.impl;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.exp.ResultPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.exp.ThinProfileDTO;
import com.testingez.testingez.models.dtos.ninja.FeedbackDTO;
import com.testingez.testingez.services.*;
import com.testingez.testingez.web.UserController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final TestService testService;
    private final NinjaService ninjaService;
    private final ResultService resultService;
    private final UserHelperService userHelperService;

    @ModelAttribute("avatarUrl")
    public String avatarUrl() {
        return this.userHelperService.getLoggedUser().getAvatarUrl();
    }

    /*
     * This method leads to the home page. It fetches the data, required for the
     * home page to be functioning correctly from the separate microservice, and
     * then passes it to the template. It throws custom exception, when the
     * microservice does not respond.
     */
    @Override
    @GetMapping("/home")
    public String home(Model model) throws NinjaMicroServiceException {
        model.addAttribute("testOfTheDay", this.ninjaService.fetchTrivia());
        model.addAttribute("factsOfTheDay", this.ninjaService.fetchFacts());
        model.addAttribute("jokesOfTheDay", this.ninjaService.fetchJokes());
        model.addAttribute("quotesOfTheDay", this.ninjaService.fetchQuotes());
        model.addAttribute("feedbackAvailable", !this.ninjaService.fetchFeedback().isEmpty());
        return "user-home";
    }

    /*
     * This method leads to the user's profile. It gets the user profile data and
     * passes it to the template. This is where users can check upon their
     * own profiles.
     */
    @Override
    @GetMapping("/my-profile")
    public String profile(Model model) {
        Long id = this.userHelperService.getLoggedUser().getId();
        model.addAttribute("userProfileData", this.userService.getProfileData(id));
        model.addAttribute("id", id);
        return "my-profile";
    }

    /*
     * This method leads to page where users are allowed to edit their profile data.
     * If the user hits the endpoint before they have made changes, the model will
     * accept the profile data from the database. Otherwise, when redirected due to
     * invalid data given, the model will pass the data that is not accepted and will
     * display appropriate messages in order to help the user understand the problem.
     */
    @Override
    @GetMapping("/my-profile/edit")
    public String edit(Model model) {
        if (!model.containsAttribute("userProfileData")) {
            model.addAttribute("userProfileData", this.userService.getProfileData(this.userHelperService.getLoggedUser().getId()));
        }
        return "my-profile-edit";
    }

    /*
     * This method receives the object that holds the edited profile data. First,
     * it validates the object. Then, if there is a problem, redirects the user to
     * the upper method, where the error messages are displayed. The invalid data is
     * sent too, so there is no need for the user to type it over and over again. If
     * there is no problem with the new edited data, the object is passed to a method
     * that tries to apply the changes. There are 4 scenarios:
     *   1.The user tries to use already taken username. (gets redirected back to change it)
     *   2.The user tries to use already taken email. (gets redirected back to change it)
     *   3.The user tries to use already taken phone. (gets redirected back to change it)
     *   4.The user profile is updated successfully.
     * After successful update, the user is sent to a page saying changes are made.
     */
    @Override
    @PostMapping("/my-profile/edit")
    public String edit(@Valid UserProfileDTO userProfileData,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return "redirect:/user/my-profile/edit";
        }

        String result = this.userService.editProfileData(this.userHelperService.getLoggedUser().getId(), userProfileData);

        if (!result.equals("success")) {
            handleProfileEditErrors(result, userProfileData, redirectAttributes);
            return "redirect:/user/my-profile/edit";
        }

        redirectAttributes.addFlashAttribute("message", "profile updated successfully");
        return "redirect:/operation/success";
    }

    /*
     * This method leads to a page where the profile of the user with the given id
     * is exposed. This endpoint is accessible only by administrators, since
     * to be redirected here, you should have got to the pages, which only
     * administrators are allowed to see.
     */
    @Override
    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("userProfileData", this.userService.getProfileData(id));
        return "user-profile";
    }

    /*
     * This method leads to a page where the administrators can edit a specific user's profiles.
     * The id required is the user's id. Using this id, the page is loaded with the data about
     * the user. This endpoint is accessible only by administrators.
     */
    @Override
    @GetMapping("/profile/{id}/edit")
    public String userEdit(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        if (!model.containsAttribute("userProfileData")) {
            model.addAttribute("userProfileData", this.userService.getProfileData(id));
        }
        return "user-profile-edit";
    }

    /*
     * This method accepts updated data about users and tries to apply the changes in the database.
     * This endpoint is accessible only by administrators. The method needs id and the object,
     * holding the new data. Then this info is passed to editProfileData() method and the result
     * determines the exit. If not successful, the administrator is redirected to page with the invalid
     * data loaded, waiting to be edited. Otherwise - home page.
     */
    @Override
    @PostMapping("/profile/{id}/edit")
    public String userEdit(@PathVariable Long id,
                           @Valid UserProfileDTO userProfileData,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileData",
                    bindingResult);
            redirectAttributes.addFlashAttribute("userProfileData", userProfileData);
            return String.format("redirect:/user/profile/%d/edit", id);
        }

        String result = this.userService.editProfileData(id, userProfileData);

        if (!result.equals("success")) {
            handleProfileEditErrors(result, userProfileData, redirectAttributes);
            return String.format("redirect:/user/profile/%d/edit", id);
        }

        redirectAttributes.addFlashAttribute("message", "profile updated successfully");
        return "redirect:/operation/success";
    }

    /*
     * This method returns the page where the user should put down
     * specific sentence in order to confirm the account deletion.
     * The model receives the user id, so later it can be passed as
     * a request parameter to another method and be eventually be deleted.
     */
    @Override
    @GetMapping("/profile/{id}/delete/confirmation")
    public String delete(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "account-delete";
    }

    /*
     * This method receives the user id as a request parameter and checks
     * if the current user id and the given id are equal. Then an attempt
     * is made the account to be deleted. And if so: we check then if the
     * user tries not delete their own account, or it is administrator
     * who deletes someone else account. If the user tries to delete their
     * own account, then the session is invalidated and redirected to the index
     * page. If not, the administrator is redirected to /user/all.
     */
    @Override
    @GetMapping("/profile/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         HttpServletRequest request, HttpServletResponse response) {
        boolean isCurrentLoggedUser = this.userHelperService.getLoggedUser().getId().equals(id);
        Boolean accountIsDeleted = this.userService.deleteProfile(id);
        if (accountIsDeleted) {
            if (isCurrentLoggedUser) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null) {
                    new SecurityContextLogoutHandler().logout(request, response, auth);
                }
                return "redirect:/";
            } else {
                return "redirect:/user/all";
            }
        } else {
            throw new UnsupportedOperationException("Sorry, we are working to solve the problem!");
        }
    }

    /*
     * This method leads to the page where users can change their avatar.
     */
    @Override
    @GetMapping("/profile/avatar/change")
    public String avatar() {
        return "avatar-change";
    }

    /*
     * This method leads to page where up to 12 user profiles are revealed.
     * It waits to pageable holding profile DTOs containing only the username
     * and the profile avatar. It is passed to the model and the page is shown.
     */
    @Override
    @GetMapping("/all")
    public String all(Pageable pageable, Model model) {
        Page<ThinProfileDTO> paginatedProfiles = this.userService.getAllPaginatedProfiles(pageable);
        model.addAttribute("paginatedProfiles", paginatedProfiles);
        return "all-users";
    }

    /*
     * This method leads the users to page, where their own tests are displayed.
     * Only 5 tests are shown at a time. Pages are available to navigate over
     * the tests. Small portion of data is shown for each test. There is button
     *  for more details on each test.
     */
    @Override
    @GetMapping("/my-tests")
    public String tests(Pageable pageable, Model model) {
        Page<TestPeekDTO> paginatedTests = this.testService.getSomePaginatedTests(pageable);
        model.addAttribute("paginatedTests", paginatedTests);
        return "my-tests";
    }

    /*
     * This method leads the users to page, where their own results are displayed.
     * Only 5 results are shown at a time. Pages are available to navigate over the tests.
     * Small portion of data is shown for each result. There is button for more details
     * on each result.
     */
    @Override
    @GetMapping("/my-results")
    public String results(Pageable pageable, Model model) {
        Page<ResultPeekDTO> paginatedResults = this.resultService.getPaginatedResults(pageable);
        model.addAttribute("paginatedResults", paginatedResults);
        return "my-results";
    }

    private void handleProfileEditErrors(String result, UserProfileDTO userProfileData, RedirectAttributes redirectAttributes) {
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
    }

}
