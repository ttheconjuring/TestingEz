package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.exp.ThinProfileDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.Role;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.RoleRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserHelperService;
import com.testingez.testingez.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final UserHelperService userHelperService;

    /*
     * This method accepts object holding all the data required for the users to create
     * their accounts. First, the method checks if the data is valid by checking its
     * uniqueness. If there is problem, the result containing the errors is returned.
     * If the data is alright, then it is mapped to entity object (dto -> entity).
     * The password is encoded, then stored. And finally, a role is attached to any account.
     * The first one inserted in table users is ADMINISTRATOR, everyone is STANDARD user.
     * The user is saved in the database and result "success" is returned.
     */
    @Override
    public String register(UserSignUpDTO userSignUpData) {
        String result = verifyUniqueCredentials(userSignUpData);
        if (!result.isEmpty()) {
            return result;
        }
        User newUser = this.modelMapper.map(userSignUpData, User.class);
        newUser.setPassword(passwordEncoder.encode(userSignUpData.getPassword()));
        newUser.setAvatarUrl("https://res.cloudinary.com/ditl40ows/image/upload/v1725455132/TestingEz/astronaut_zfqyvs.png");
        if (this.userRepository.count() == 0) {
            newUser.setRole(this.roleRepository.findById(1L)
                    .orElseThrow(() -> new IllegalArgumentException("No role could be found with id: 1")));
        } else {
            newUser.setRole(this.roleRepository.findById(2L)
                    .orElseThrow(() -> new IllegalArgumentException("No role could be found with id: 2")));
        }
        this.userRepository.saveAndFlush(newUser);
        return "success";
    }

    /*
     * This method finds the current logged user,
     * gets its profile data, maps it to DTO and returns the dto.
     */
    @Override
    public UserProfileDTO getProfileData(Long id) {
        return this.modelMapper.map(
                this.userRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("We couldn't find user with id: " + id)
                ), UserProfileDTO.class);
    }

    /*
     * This method accepts object holding potentially updated profile data. Then this object
     * is passed to another method that takes care of updating the values in the database,
     * along with the current logger user object.
     */
    @Override
    public String editProfileData(Long id, UserProfileDTO userProfileData) {
        return updateUserProfileData(userProfileData, this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("We couldn't find user with id: " + id)
        ));
    }

    /*
     * This method receives user id and tries to delete the user. If the deletion
     * is complete, true is returned, otherwise - false.
     */
    @Override
    public Boolean deleteProfile(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * This method accepts url leading to the image on internet. If
     * the url is null or empty string, then error is thrown! Otherwise,
     * the image is set the current user account.
     */
    @Override
    public void changeAvatar(String url) {
        if (url != null && !url.isEmpty()) {
            this.userHelperService.getLoggedUser().setAvatarUrl(url);
            this.userRepository.saveAndFlush(this.userHelperService.getLoggedUser());
        } else {
            throw new IllegalArgumentException("The provided avatar url is invalid!");
        }
    }

    /*
     * This method retrieves all users from the database, maps each user
     * to ThinProfileDTO and returns the page.
     */
    @Override
    public Page<ThinProfileDTO> getAllPaginatedProfiles(Pageable pageable) {
        Page<User> users = this.userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user, ThinProfileDTO.class));
    }

    /*
     * This method is invoked in register(). It checks if the the username, the email
     * and the phone are actually unique values. If there is some violations, then the
     * errors are stored in string builder object and then returned. It also checks, if
     * the password and the confirm password inputs are equal. If not, another error is
     * added to the string builder object. At the end the result is returned. If it is
     * empty, that means everything is alright. If it is not empty, there are some problems
     * that should be solved.
     */
    protected String verifyUniqueCredentials(UserSignUpDTO userSignUpData) {
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


    /*
     * This method accepts the updated profile data, then validates it and finally updates it.
     * The username, the email and the phone should be unique values, otherwise they are not
     * accepted. If there is some violations, the error string object is returned immediately.
     * If the there are no errors so far, we check if the there is difference between the current
     * user names and the new one. If so, we update them. Finally, the updated user object is saved
     * in the database (updated) and string "success" is returned.
     */
    protected String updateUserProfileData(UserProfileDTO userProfileData, User user) {
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
            return errors.trim();
        }
        if (!userProfileData.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(userProfileData.getFirstName());
        }
        if (!userProfileData.getLastName().equals(user.getLastName())) {
            user.setLastName(userProfileData.getLastName());
        }
        if (!userProfileData.getRole().equals(user.getRole().getRole().name())) {
            if (userProfileData.getRole().equals("ADMINISTRATOR")) {
                user.setRole(this.roleRepository.findById(1L).get());
            } else {
                user.setRole(this.roleRepository.findById(2L).get());
            }
        }
        this.userRepository.saveAndFlush(user);
        return "success";
    }
}

