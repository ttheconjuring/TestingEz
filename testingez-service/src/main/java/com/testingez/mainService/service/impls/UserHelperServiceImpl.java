package com.testingez.mainService.service.impls;

import com.testingez.mainService.model.entities.User;
import com.testingez.mainService.repository.UserRepository;
import com.testingez.mainService.service.UserHelperService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserHelperServiceImpl implements UserHelperService {

    private final UserRepository userRepository;

    @Override
    public UserDetails getLoggedUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    @Override
    public User getLoggedUser() {
        return this.userRepository.findByUsername(
                        getLoggedUserDetails()
                                .getUsername())
                .orElseThrow(() ->
                        new UsernameNotFoundException("No user found with username: " + getLoggedUserDetails().getUsername()));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
