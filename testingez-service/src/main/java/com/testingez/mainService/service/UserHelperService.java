package com.testingez.mainService.service;

import com.testingez.mainService.model.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserHelperService {

    UserDetails getLoggedUserDetails();

    User getLoggedUser();

}
