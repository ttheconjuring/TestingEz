package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    boolean login(UserSignInDTO userSingInData);

    UserProfileDTO getUserProfileData(Long id);

    String editProfileData(UserProfileDTO userProfileData, Long id);
}
