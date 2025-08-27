package com.testingez.mainService.services;

import com.testingez.mainService.models.dtos.UserProfileDTO;
import com.testingez.mainService.models.dtos.exp.ThinProfileDTO;
import com.testingez.mainService.models.dtos.imp.UserSignUpDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    String register(UserSignUpDTO userSignUpData);

    UserProfileDTO getProfileData(Long id);

    String editProfileData(Long id, UserProfileDTO userProfileData);

    Boolean deleteProfile(Long id);

    void changeAvatar(String url);

    Page<ThinProfileDTO> getAllPaginatedProfiles(Pageable pageable);
}
