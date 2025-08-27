package com.testingez.testingez.services;

import com.testingez.testingez.models.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserHelperService {

    UserDetails getLoggedUserDetails();

    User getLoggedUser();

}
