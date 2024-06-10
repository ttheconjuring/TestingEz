package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.UserSignInDataDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import org.springframework.validation.BindingResult;

public interface UserService {

    void register(UserSignUpDataDTO userSignUpData);

    void login(UserSignInDataDTO userSingInData, BindingResult bindingResult);
}
