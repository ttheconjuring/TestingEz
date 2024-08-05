package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.Role;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.repositories.RoleRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
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
    @Mock
    private UserHelperService userHelperService;

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

    @Test
    void getUserProfileDataMethodShouldSuccessfullyReturnUserProfileData() {
        // given
        User loggedUser = SampleObjects.user();
        loggedUser.setRole(SampleObjects.standardRole());
        UserProfileDTO profileData = SampleObjects.userProfileDTO();

        when(userHelperService.getLoggedUser()).thenReturn(loggedUser);
        when(modelMapper.map(loggedUser, UserProfileDTO.class)).thenReturn(profileData);

        // when
        UserProfileDTO userProfileData = underTest.getUserProfileData();

        // then
        assertThat(userProfileData.getUsername()).isEqualTo(loggedUser.getUsername());
        assertThat(userProfileData.getFirstName()).isEqualTo(loggedUser.getFirstName());
        assertThat(userProfileData.getLastName()).isEqualTo(loggedUser.getLastName());
        assertThat(userProfileData.getEmail()).isEqualTo(loggedUser.getEmail());
        assertThat(userProfileData.getPhone()).isEqualTo(loggedUser.getPhone());
        assertThat(userProfileData.getRole()).isEqualTo(loggedUser.getRole().getRole().name());
        assertThat(userProfileData).isEqualTo(profileData);
    }

    @Test
    void getUserProfileDataMethodShouldReturnNullWhenLoggedUserIsNull() {
        // given
        when(userHelperService.getLoggedUser()).thenReturn(null);

        // when
        UserProfileDTO userProfileData = underTest.getUserProfileData();

        // then
        assertThat(userProfileData).isNull();
    }

    @Test
    void getUserProfileDataMethodShouldThrowAnErrorWhenThereIsProblemWithMapping() {
        // given
        User loggedUser = SampleObjects.user();

        when(userHelperService.getLoggedUser()).thenReturn(loggedUser);
        when(modelMapper.map(loggedUser, UserProfileDTO.class))


                // when
                .thenThrow(new MappingException(List.of((
                        new ErrorMessage("Mapping was not possible!")))));

        // then
        assertThatExceptionOfType(MappingException.class)
                .isThrownBy(() -> underTest.getUserProfileData())
                .withMessageContaining("Mapping was not possible!");
    }

    @Test
    void editProfileDataMethodShouldSuccessfullyEditAllTheProfileData() {
        // given
        UserProfileDTO updated = new UserProfileDTO();
        updated.setUsername("updated");
        updated.setFirstName("updated");
        updated.setLastName("updated");
        updated.setEmail("updated@email.com");
        updated.setPhone("updated-phone");
        User current = SampleObjects.user();

        when(userHelperService.getLoggedUser()).thenReturn(current);
        when(userRepository.findByUsername(updated.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(updated.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(updated.getPhone())).thenReturn(Optional.empty());

        // when
        String result = underTest.editProfileData(updated);

        // then
        assertThat(result).isEqualTo("success");
        assertThat(current.getUsername()).isEqualTo(updated.getUsername());
        assertThat(current.getFirstName()).isEqualTo(updated.getFirstName());
        assertThat(current.getLastName()).isEqualTo(updated.getLastName());
        assertThat(current.getEmail()).isEqualTo(updated.getEmail());
        assertThat(current.getPhone()).isEqualTo(updated.getPhone());
    }

    @Test
    void editProfileDataMethodShouldNotUpdateUsernameEmailAndPhoneWhenTheyAreAlreadyTaken() {
        // given
        UserProfileDTO updated = new UserProfileDTO();
        updated.setUsername("updated");
        updated.setFirstName("updated");
        updated.setLastName("updated");
        updated.setEmail("updated@email.com");
        updated.setPhone("updated-phone");
        User current = SampleObjects.user();

        when(userHelperService.getLoggedUser()).thenReturn(current);
        when(userRepository.findByUsername(updated.getUsername())).thenReturn(Optional.of(current));
        when(userRepository.findByEmail(updated.getEmail())).thenReturn(Optional.of(current));
        when(userRepository.findByPhone(updated.getPhone())).thenReturn(Optional.of(current));

        // when
        String result = underTest.editProfileData(updated);

        // then
        assertThat(result).isNotEqualTo("success");
        assertThat(current.getUsername()).isNotEqualTo(updated.getUsername());
        assertThat(current.getEmail()).isNotEqualTo(updated.getEmail());
        assertThat(current.getPhone()).isNotEqualTo(updated.getPhone());
        assertThat(result).contains("username")
                .contains("email")
                .contains("phone");

    }


}