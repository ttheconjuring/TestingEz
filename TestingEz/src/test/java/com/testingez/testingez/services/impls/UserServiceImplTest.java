package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.Role;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.repositories.RoleRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl underTest;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void registerMethodShouldReturnSuccess() {
        // given
        UserSignUpDTO userSignUpData = SampleObjects.userSignUpDTO();
        User user = SampleObjects.user();
        Role adminRole = SampleObjects.adminRole();

        when(userRepository.findByUsername(userSignUpData.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userSignUpData.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(userSignUpData.getPhone())).thenReturn(Optional.empty());
        when(modelMapper.map(userSignUpData, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userSignUpData.getPassword())).thenReturn("encoded-password");
        when(userRepository.count()).thenReturn(0L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(adminRole));

        // when
        String result = underTest.register(userSignUpData);

        // then
        assertThat(result).isEqualTo("success");
    }

    @Test
    void registerMethodShouldReturnErrorStringWhenUsernameEmailPhoneTheyAreAlreadyTaken() {
        // given
        UserSignUpDTO userSignUpDTO = SampleObjects.userSignUpDTO();
        User user = SampleObjects.user();

        when(userRepository.findByUsername(userSignUpDTO.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userSignUpDTO.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByPhone(userSignUpDTO.getPhone())).thenReturn(Optional.of(user));

        // when
        String result = underTest.register(userSignUpDTO);

        // then
        assertThat(result).contains("username");
        assertThat(result).contains("email");
        assertThat(result).contains("phone");
    }

    @Test
    void registerMethodShouldReturnErrorStringWhenThePasswordAndTheConfirmPasswordInputsAreNotEqual() {
        // given
        UserSignUpDTO userSignUpDTO = SampleObjects.userSignUpDTO();
        userSignUpDTO.setConfirmPassword("random");

        when(userRepository.findByUsername(userSignUpDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userSignUpDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(userSignUpDTO.getPhone())).thenReturn(Optional.empty());

        // when
        String result = underTest.register(userSignUpDTO);

        // then
        assertThat(result).contains("passwords");
    }

}