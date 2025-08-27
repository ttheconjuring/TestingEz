package com.testingez.mainService.services;

import com.testingez.mainService.models.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserHelperService {

    UserDetails getLoggedUserDetails();

    User getLoggedUser();

}
