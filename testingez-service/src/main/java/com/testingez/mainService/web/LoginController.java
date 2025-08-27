package com.testingez.mainService.web;

import org.springframework.ui.Model;

public interface LoginController {

    String login(String error, Model model);

}
