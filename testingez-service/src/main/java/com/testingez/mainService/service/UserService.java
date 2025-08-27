package com.testingez.mainService.service;

import com.testingez.mainService.model.dtos.UserProfileDTO;
import com.testingez.mainService.model.dtos.exp.ThinProfileDTO;
import com.testingez.mainService.model.dtos.imp.UserSignUpDTO;
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
