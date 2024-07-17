package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.exp.TestPeekDTO;
import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    UserProfileDTO getUserProfileData();

    String editProfileData(UserProfileDTO userProfileData);

    Page<TestPeekDTO> getPaginatedTests(Pageable pageable);

}
