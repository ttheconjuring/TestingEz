package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import org.springframework.validation.BindingResult;

public interface UserService {

    void register(UserSignUpDTO userSignUpData);

    void login(UserSignInDTO userSingInData, BindingResult bindingResult);
}
