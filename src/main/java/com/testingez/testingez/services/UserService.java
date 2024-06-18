package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    boolean login(UserSignInDTO userSingInData);

}
