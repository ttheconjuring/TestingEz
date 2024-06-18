package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.UserSignInDTO;
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
        String result = validate(userSignUpData);
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

    @Override
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
    }

    private String validate(UserSignUpDTO userSignUpData) {
        String error = "";
        Optional<User> byUsername = this.userRepository.findByUsername(userSignUpData.getUsername());
        if (byUsername.isPresent()) {
            error += ("username ");
        }
        Optional<User> byEmail = this.userRepository.findByEmail(userSignUpData.getEmail());
        if (byEmail.isPresent()) {
            error += ("email ");
        }
        Optional<User> byPhone = this.userRepository.findByPhone(userSignUpData.getPhone());
        if (byPhone.isPresent()) {
            error += "phone ";
        }
        if (!userSignUpData.getPassword().equals(userSignUpData.getConfirmPassword())) {
            error += "passwords ";
        }
        return error.isEmpty() ? "" : error;
    }
}

