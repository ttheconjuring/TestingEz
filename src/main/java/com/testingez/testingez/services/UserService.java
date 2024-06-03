package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.UserLoginDataDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import org.springframework.validation.BindingResult;

public interface UserService {

    void register(UserSignUpDataDTO userSignUpData);

    void confirmPasswords(UserSignUpDataDTO userSignUpDataDTO, BindingResult bindingResult);

    void login(UserLoginDataDTO userLoginData, BindingResult bindingResult);
}
