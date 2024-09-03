package com.testingez.testingez.web.impl;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.exp.ResultPeekDTO;
import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import com.testingez.testingez.services.*;
import com.testingez.testingez.web.UserController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final TestService testService;
    private final NinjaService ninjaService;
    private final ResultService resultService;
    private final UserHelperService userHelperService;

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
        model.addAttribute("ideasAvailable", !this.ninjaService.fetchImprovements().isEmpty());
        if (!model.containsAttribute("improvementData")) {
            model.addAttribute("improvementData", new ImprovementDTO());
        }
        return "user-home";
    }

    /*
     * This method leads to the user's profile. It gets the user profile data and
     * passes it to the template. This is where users can check upon their
     * own profiles.
     */
    @Override
    @GetMapping("/profile")
    public String profile(Model model) {
        addUserProfileDataToModel(model);
        return "user-profile";
    }

    /*
     * This method leads to page where users are allowed to edit their profile data.
     * If the user hits the endpoint before they have made changes, the model will
     * accept the profile data from the database. Otherwise, when redirected due to
     * invalid data given, the model will pass the data that is not accepted and will
     * display appropriate messages in order to help the user understand the problem.
     */
    @Override
    @GetMapping("/profile/edit")
    public String edit(Model model) {
        if (!model.containsAttribute("userProfileData")) {
            addUserProfileDataToModel(model);
        }
        return "user-profile-edit";
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
            handleProfileEditErrors(result, userProfileData, redirectAttributes);
            return "redirect:/user/profile/edit";
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
    @GetMapping("/profile/delete/confirmation")
    public String delete(Model model) {
        model.addAttribute("id", this.userHelperService.getLoggedUser().getId());
        return "account-delete";
    }

    /*
     * This method receives the user id as a request parameter and invokes
     * method that tries to delete the user. If the account is deleted, then
     * the user is redirected to the index page, otherwise an exception is
     * thrown with an appropriate message.
     */
    @Override
    @GetMapping("/profile/delete")
    public String delete(@RequestParam(name = "id") Long id) {
        Boolean accountIsDeleted = this.userService.deleteProfile(id);
        if (accountIsDeleted) {
            return "redirect:/";
        } else {
            throw new UnsupportedOperationException("Sorry, we are working to solve the problem!");
        }
    }

    /*
     * This method leads the users to page, where their own tests are displayed.
     * Only 5 tests are shown at a time. Pages are available to navigate over
     * the tests. Small portion of data is shown for each test. There is button
     *  for more details on each test.
     */
    @Override
    @GetMapping("/my-tests")
    public String userTests(Pageable pageable, Model model) {
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
    public String userResults(Pageable pageable, Model model) {
        Page<ResultPeekDTO> paginatedResults = this.resultService.getPaginatedResults(pageable);
        model.addAttribute("paginatedResults", paginatedResults);
        return "my-results";
    }

    /*
     * This method accepts the object sent from user home page that contains the improvement idea.
     * The object is validated and if there are violations, then users are alerted. Then, a POST
     * request is made with rest client to endpoint: /ninja/api/improvements/post where
     * the data is proceed by the rest controller and put in the database. If the request
     * is successful, a polite green-text message is shown, indicating the job is done.
     */
    @Override
    @PostMapping("/improvements/post")
    public String postImprovement(@Valid ImprovementDTO improvementData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.improvementData", bindingResult);
            redirectAttributes.addFlashAttribute("improvementData", improvementData);
            return "redirect:/user/home";
        }

        try {
            this.ninjaService.postImprovement(improvementData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("sent", true);
        return "redirect:/user/home";
    }

    /*
     * This method leads to the page where the users improvement ideas are revealed.
     * Here, admin can approve and disapprove them by clicking buttons. The data
     * is fetched from the microservice and passed to the model.
     */

    @Override
    @GetMapping("/improvement/ideas")
    public String checkImprovements(Model model) {
        List<ImprovementDTO> improvementIdeas;
        try {
            improvementIdeas = this.ninjaService.fetchImprovements();
        } catch (NinjaMicroServiceException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("improvementIdeas", improvementIdeas);
        return "impr-ideas";
    }

    /*
     * This method accepts UUID id and tries to delete the improvement idea. After that,
     * users are redirected to the page with all improvement ideas. If an idea is deleted
     * successfully, there should be some sort of alert, saying the process was done. Otherwise,
     * another alert should appear, telling the users that the improvement idea was not deleted
     * due to some reasons.
     */

    @Override
    @GetMapping("/improvements/delete/{id}")
    public String deleteImprovement(@PathVariable UUID id) {
        try {
            this.ninjaService.deleteImprovement(id);
        } catch (NinjaMicroServiceException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/user/improvement/ideas";
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

    private void addUserProfileDataToModel(Model model) {
        model.addAttribute("userProfileData", this.userService.getProfileData());
    }

}
