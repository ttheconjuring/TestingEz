package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.CurrentUser;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Override
    public String register(UserSignUpDTO userSignUpData) {
        String result = verifyUniqueCredentials(userSignUpData);
        if (!result.isEmpty()) {
            return result;
        }
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        newUser.setPassword(passwordEncoder.encode(userSignUpData.getPassword()));
        if (this.userRepository.count() == 0) {
            newUser.setRole(UserRole.ADMINISTRATOR);
        } else {
            newUser.setRole(UserRole.STANDARD);
        }
        this.userRepository.saveAndFlush(newUser);
        this.currentUser.setUser(newUser);
        return "success";
    }

    /* @Override
    public boolean login(UserSignInDTO userSingInData) {
        Optional<User> byUsername = this.userRepository.findByUsername(userSingInData.getUsername());
        if (byUsername.isEmpty()) {
            return false;
        }
        User user = byUsername.get();
        if (!this.passwordEncoder.matches(userSingInData.getPassword(), user.getPassword())) {
            return false;
        }
        this.currentUser.setUser(user);
        return true;
    } */

    @Override
    public UserProfileDTO getUserProfileData(Long id) {
        Optional<User> byId = this.userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("No user with id = " + id + " found.");
        }
        return this.modelMapper.map(byId.get(), UserProfileDTO.class);
    }

    @Override
    public String editProfileData(UserProfileDTO userProfileData, Long id) {
        Optional<User> byId = this.userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("No user with id = " + id + " found.");
        }
        return updateUserProfileData(userProfileData, byId.get());
    }

    private String verifyUniqueCredentials(UserSignUpDTO userSignUpData) {
        String errors = "";
        Optional<User> byUsername = this.userRepository.findByUsername(userSignUpData.getUsername());
        if (byUsername.isPresent()) {
            errors += ("username ");
        }
        Optional<User> byEmail = this.userRepository.findByEmail(userSignUpData.getEmail());
        if (byEmail.isPresent()) {
            errors += ("email ");
        }
        Optional<User> byPhone = this.userRepository.findByPhone(userSignUpData.getPhone());
        if (byPhone.isPresent()) {
            errors += "phone ";
        }
        if (!userSignUpData.getPassword().equals(userSignUpData.getConfirmPassword())) {
            errors += "passwords ";
        }
        return errors.isEmpty() ? "" : errors;
    }

    private String updateUserProfileData(UserProfileDTO userProfileData, User user) {
        String errors = "";
        if (!userProfileData.getUsername().equals(user.getUsername())) {
            if (this.userRepository.findByUsername(userProfileData.getUsername()).isEmpty()) {
                user.setUsername(userProfileData.getUsername());
                this.currentUser.setUsername(user.getUsername());
            } else {
                errors += ("username ");
            }
        }
        if (!userProfileData.getEmail().equals(user.getEmail())) {
            if (this.userRepository.findByEmail(userProfileData.getEmail()).isEmpty()) {
                user.setEmail(userProfileData.getEmail());
            } else {
                errors += ("email ");
            }
        }
        if (!userProfileData.getPhone().equals(user.getPhone())) {
            if (this.userRepository.findByPhone(userProfileData.getPhone()).isEmpty()) {
                user.setPhone(userProfileData.getPhone());
            } else {
                errors += ("phone ");
            }
        }
        if (!errors.isEmpty()) {
            return errors;
        }
        if (!userProfileData.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(userProfileData.getFirstName());
        }
        if (!userProfileData.getLastName().equals(user.getLastName())) {
            user.setLastName(userProfileData.getLastName());
        }
        this.userRepository.saveAndFlush(user);
        return "success";
    }
}

