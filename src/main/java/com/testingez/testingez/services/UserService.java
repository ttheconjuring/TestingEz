package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;

public interface UserService {

    void register(UserSignUpDataDTO userSignUpData);

}
