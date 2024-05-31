package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.UserSignUpDataDTO;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void register(UserSignUpDataDTO userSignUpData) {
        Optional<User> byUsername = this.userRepository.findByUsername(userSignUpData.getUsername());
        if (byUsername.isPresent()) {
            throw new InvalidParameterException("Username already exists");
        }
        Optional<User> byEmail = this.userRepository.findByEmail(userSignUpData.getEmail());
        if (byEmail.isPresent()) {
            throw new InvalidParameterException("Email already exists");
        }
        Optional<User> byPhone = this.userRepository.findByPhone(userSignUpData.getPhone());
        if (byPhone.isPresent()) {
            throw new InvalidParameterException("Phone already exists");
        }
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        if (this.userRepository.count() == 0) {
            newUser.setRole(UserRole.ADMINISTRATOR);
        } else {
            newUser.setRole(UserRole.STANDARD);
        }
        this.userRepository.saveAndFlush(newUser);
    }

}
