package com.testingez.testingez.web;

import com.testingez.testingez.models.dtos.TestJoinDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
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

    String showAll(Pageable pageable, Model model);

}
