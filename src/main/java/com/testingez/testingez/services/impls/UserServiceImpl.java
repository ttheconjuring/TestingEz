package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.repositories.RoleRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public String register(UserSignUpDTO userSignUpData) {
        String result = verifyUniqueCredentials(userSignUpData);
        if (!result.isEmpty()) {
            return result;
        }
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        newUser.setPassword(passwordEncoder.encode(userSignUpData.getPassword()));
        if (this.userRepository.count() == 0) {
            newUser.setRole(this.roleRepository.findById(2L)
                    .orElseThrow(() -> new IllegalArgumentException("No role could be found with id: 2")));
        } else {
            newUser.setRole(this.roleRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("No role could be found with id: 1")));
        }
        this.userRepository.saveAndFlush(newUser);
        return "success";
    }
  
    @Override
    public UserProfileDTO getUserProfileData(String username) {
        return this.modelMapper.map(this.userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("No user found with username: " + username + "!")), UserProfileDTO.class);
    }

    @Override
    public String editProfileData(UserProfileDTO userProfileData, String username) {
        return updateUserProfileData(userProfileData,
                this.userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username + "!")));
    }

    private String verifyUniqueCredentials(UserSignUpDTO userSignUpData) {
        StringBuilder errors = new StringBuilder();
        // List of checks to perform
        List<Supplier<Optional<String>>> checks = Arrays.asList(
                () -> this.userRepository.findByUsername(userSignUpData.getUsername()).map(user -> "username "),
                () -> this.userRepository.findByEmail(userSignUpData.getEmail()).map(user -> "email "),
                () -> this.userRepository.findByPhone(userSignUpData.getPhone()).map(user -> "phone ")
        );
        // Perform each check and append any errors found
        checks.stream()
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(errors::append);
        // Check password confirmation
        if (!userSignUpData.getPassword().equals(userSignUpData.getConfirmPassword())) {
            errors.append("passwords ");
        }
        return errors.toString().trim();
    }


    private String updateUserProfileData(UserProfileDTO userProfileData, User user) {
        String errors = "";
        if (!userProfileData.getUsername().equals(user.getUsername())) {
            if (this.userRepository.findByUsername(userProfileData.getUsername()).isEmpty()) {
                user.setUsername(userProfileData.getUsername());
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

