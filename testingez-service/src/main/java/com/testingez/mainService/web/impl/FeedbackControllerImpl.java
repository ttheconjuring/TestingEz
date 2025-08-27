package com.testingez.mainService.web.impl;

import com.testingez.mainService.exceptions.custom.NinjaMicroServiceException;
import com.testingez.mainService.models.dtos.ninja.FeedbackDTO;
import com.testingez.mainService.services.NinjaService;
import com.testingez.mainService.services.UserHelperService;
import com.testingez.mainService.web.FeedbackController;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/feedback")
@AllArgsConstructor
public class FeedbackControllerImpl implements FeedbackController {

    private final NinjaService ninjaService;
    private final UserHelperService userHelperService;

    @Override
    @GetMapping
    public String feedback(Model model) {
        if (!model.containsAttribute("feedbackDTO")) {
            model.addAttribute("feedbackDTO", new FeedbackDTO());
        }
        return "feedback";
    }

    /*
     * This method accepts the object sent from user home page that contains the improvement idea.
     * The object is validated and if there are violations, then users are alerted. Then, a POST
     * request is made with rest client to endpoint: /ninja/api/improvements/post where
     * the data is proceeded by the rest controller and put in the database. If the request
     * is successful, a polite green-text message is shown, indicating the job is done.
     */
    @Override
    @PostMapping("/post")
    public String post(@Valid FeedbackDTO feedbackDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) throws NinjaMicroServiceException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.feedbackDTO", bindingResult);
            redirectAttributes.addFlashAttribute("feedbackDTO", feedbackDTO);
            return "redirect:/feedback";
        }
        this.ninjaService.postFeedback(feedbackDTO);
        redirectAttributes.addFlashAttribute("sent", true);
        return "redirect:/feedback";
    }

    /*
     * This method leads to the page where the users improvement ideas are revealed.
     * Here, admin can approve and disapprove them by clicking buttons. The data
     * is fetched from the microservice and passed to the model.
     */
    @Override
    @GetMapping("/all")
    public String check(Model model) throws NinjaMicroServiceException {
        List<FeedbackDTO> feedbacks = this.ninjaService.fetchFeedback();
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("avatarUrl", this.userHelperService.getLoggedUser().getAvatarUrl());
        return "feedback-ideas";
    }

    /*
     * This method accepts UUID id and tries to approve the feedback. After that,
     * users are redirected to the page with all feedbacks. If an feedback is approved
     * successfully, it appears in green color.
     */
    @Override
    @GetMapping("/approve/{id}")
    public String approve(@PathVariable UUID id) throws NinjaMicroServiceException {
        this.ninjaService.approveFeedback(id);
        return "redirect:/feedback/all";
    }

    /*
     * This method accepts UUID id and tries to disapprove the feedback. After that,
     * users are redirected to the page with all feedbacks. If an feedback is disapproved
     * successfully, it appears in red color.
     */
    @Override
    @GetMapping("/disapprove/{id}")
    public String disapprove(@PathVariable UUID id) throws NinjaMicroServiceException {
        this.ninjaService.disapproveFeedback(id);
        return "redirect:/feedback/all";
    }

}
