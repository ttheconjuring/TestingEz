package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    UserProfileDTO getUserProfileData(String username);

    String editProfileData(UserProfileDTO userProfileData, String username);

}
