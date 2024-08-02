package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void returnsNonEmptyOptionalOfExistingUserWhenExistingUsernameIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername(user.getUsername());

        // then
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingUsernameIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername("non-existing-username");

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullUsernameIsGiven() {
        // given
        String username = null;

        // when
        Optional<User> userOptional = underTest.findByUsername(username);

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void returnsNonEmptyOptionalOfExistingUserWhenExistingEmailsIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByEmail(user.getEmail());

        // then
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingEmailsIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByEmail("non-existing-email");

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullEmailIsGiven() {
        // given
        String email = null;

        // when
        Optional<User> userOptional = underTest.findByEmail(email);

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void returnsNonEmptyOptionalOfExistingUserWhenExistingPhoneIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByPhone(user.getPhone());

        // then
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingPhoneIsGiven() {
        // given
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername("non-existing-phone");

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullPhoneIsGiven() {
        // given
        String phone = null;

        // when
        Optional<User> userOptional = underTest.findByUsername(phone);

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }

}