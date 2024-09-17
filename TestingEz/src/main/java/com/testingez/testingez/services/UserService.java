package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.exp.ThinProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    UserProfileDTO getProfileData(Long id);

    String editProfileData(UserProfileDTO userProfileData);

    Boolean deleteProfile(Long id);

    void changeAvatar(String url);

    Page<ThinProfileDTO> getAllPaginatedProfiles(Pageable pageable);
}
