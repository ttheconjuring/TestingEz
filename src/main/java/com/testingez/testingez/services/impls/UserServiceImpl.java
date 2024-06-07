package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.UserLoginDataDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.UserRepository;
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

    @Override
    public void register(UserSignUpDataDTO userSignUpData) {
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        newUser.setPassword(passwordEncoder.encode(userSignUpData.getPassword()));
        if (this.userRepository.count() == 0) {
            newUser.setRole(UserRole.ADMINISTRATOR);
        } else {
            newUser.setRole(UserRole.STANDARD);
        }
        this.userRepository.saveAndFlush(newUser);
    }

    @Override
    public void login(UserLoginDataDTO userLoginData, BindingResult bindingResult) {
        Optional<User> byUsername = this.userRepository.findByUsername(userLoginData.getUsername());
        if (byUsername.isEmpty()) {
            bindingResult.addError(new FieldError("userLoginData", "username", "username or password is incorrect"));
        } else {
            User user = byUsername.get();
            if (!user.getPassword().equals(userLoginData.getPassword())) {
                bindingResult.addError(new FieldError("userLoginData", "username", "username or password is incorrect"));
            }
        }
    }

}
