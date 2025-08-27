package com.testingez.mainService.web;

import com.testingez.mainService.model.dtos.TestJoinDTO;
import com.testingez.mainService.model.dtos.imp.TestCreateDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface TestController {

    String join();

    String join(TestJoinDTO testJoinData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String preview(String code, Model model);

    String create();

    String create(TestCreateDTO testCreateData, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    String delete(Long id, RedirectAttributes redirectAttributes);

    String all(Pageable pageable, Model model);

    String leaderboard(Long id, Model model);

    String testDetails(Long id, Model model);

    String changeTestStatus(Long id);

}
