package com.testingez.testingez.web.impl;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.ImprovementDTO;
import com.testingez.testingez.services.NinjaService;
import com.testingez.testingez.web.FeedbackController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class FeedbackControllerImpl implements FeedbackController {

    private final NinjaService ninjaService;

    /*
     * This method accepts the object sent from user home page that contains the improvement idea.
     * The object is validated and if there are violations, then users are alerted. Then, a POST
     * request is made with rest client to endpoint: /ninja/api/improvements/post where
     * the data is proceed by the rest controller and put in the database. If the request
     * is successful, a polite green-text message is shown, indicating the job is done.
     */
    @Override
    public String postImprovement(ImprovementDTO improvementData, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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
    public String deleteImprovement(UUID id) {
        try {
            this.ninjaService.deleteImprovement(id);
        } catch (NinjaMicroServiceException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/user/improvement/ideas";
    }

}
