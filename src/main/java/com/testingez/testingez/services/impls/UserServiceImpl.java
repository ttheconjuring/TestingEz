package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void register(UserSignUpDataDTO userSignUpData) {
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        if (this.userRepository.count() == 0) {
            newUser.setRole(UserRole.ADMINISTRATOR);
        } else {
            newUser.setRole(UserRole.STANDARD);
        }
        this.userRepository.saveAndFlush(newUser);
    }

    @Override
    public void confirmPasswords(UserSignUpDataDTO userSignUpDataDTO, BindingResult bindingResult) {
        if (!userSignUpDataDTO.getPassword().equals(userSignUpDataDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userSignUpDataDTO", "confirmPassword", "Passwords do not match"));
        }
    }

}
