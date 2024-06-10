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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    @Override
    public void register(UserSignUpDTO userSignUpData) {
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        newUser.setPassword(passwordEncoder.encode(userSignUpData.getPassword()));
        if (this.userRepository.count() == 0) {
            newUser.setRole(UserRole.ADMINISTRATOR);
        } else {
            newUser.setRole(UserRole.STANDARD);
        }
        this.userRepository.saveAndFlush(newUser);
        logUser(newUser);
    }

    @Override
    public void login(UserSignInDTO userSingInData, BindingResult bindingResult) {
        Optional<User> byUsername = this.userRepository.findByUsername(userSingInData.getUsername());
        if (byUsername.isEmpty()) {
            bindingResult.addError(new FieldError("userSingInData", "username", "username or password is incorrect"));
        } else {
            User user = byUsername.get();
            if (!this.passwordEncoder.matches(userSingInData.getPassword(), user.getPassword())) {
                bindingResult.addError(new FieldError("userSingInData", "username", "username or password is incorrect"));
            } else
                logUser(user);
        }
    }

    private void logUser(User user) {
        if (this.currentUser.isLogged()) {
            this.currentUser.setUser(null);
        }
        this.currentUser.setUser(user);
    }

}
