package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    UserProfileDTO getUserProfileData();

    String editProfileData(UserProfileDTO userProfileData);

}
