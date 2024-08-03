package com.testingez.testingez.repositories;

import com.testingez.testingez.SampleObjects;
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
    void returnsNonEmptyOptionalOfUserWhenExistingUsernameIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername(user.getUsername());

        // then
        assertThat(userOptional).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingUsernameIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername("non-existing-username");

        // then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullUsernameIsGiven() {
        // given
        String username = null;

        // when
        Optional<User> userOptional = underTest.findByUsername(username);

        // then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void returnsNonEmptyOptionalOfUserWhenExistingEmailsIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByEmail(user.getEmail());

        // then
        assertThat(userOptional).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingEmailsIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByEmail("non-existing-email");

        // then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullEmailIsGiven() {
        // given
        String email = null;

        // when
        Optional<User> userOptional = underTest.findByEmail(email);

        // then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void returnsNonEmptyOptionalOfUserWhenExistingPhoneIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByPhone(user.getPhone());

        // then
        assertThat(userOptional).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNonExistingPhoneIsGiven() {
        // given
        User user = SampleObjects.user();
        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByUsername("non-existing-phone");

        // then
        assertThat(userOptional).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOfUserWhenNullPhoneIsGiven() {
        // given
        String phone = null;

        // when
        Optional<User> userOptional = underTest.findByUsername(phone);

        // then
        assertThat(userOptional).isEmpty();
    }

}