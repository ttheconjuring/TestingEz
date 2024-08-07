package com.testingez.testingez.repositories;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ResultRepositoryTest {

    @Autowired
    private ResultRepository underTest;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
        testRepository.deleteAll();
    }

    @Test
    void returnsNonEmptyOptionalOfResultWhenExistingTestIdAndExistingUserIdAreGiven() {
        // given
        User user = SampleObjects.user();
        userRepository.save(user);
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Result result = SampleObjects.passResult();
        result.setUser(user);
        result.setTest(test);
        underTest.save(result);

        // when
        Optional<Result> resultOptional = underTest.findByTestIdAndUserId(test.getId(), user.getId());

        // then
        assertThat(resultOptional).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfResultWhenNonExistingTestIdAndExistingUserIdAreGiven() {
        // given
        User user = SampleObjects.user();
        userRepository.save(user);
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Result result = SampleObjects.passResult();
        result.setUser(user);
        result.setTest(test);
        underTest.save(result);

        // when
        Optional<Result> resultOptional = underTest.findByTestIdAndUserId(-1L, user.getId());

        // then
        assertThat(resultOptional).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOfResultWhenExistingTestIdAndNonExistingUserIdAreGiven() {
        // given
        User user = SampleObjects.user();
        userRepository.save(user);
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Result result = SampleObjects.passResult();
        result.setUser(user);
        result.setTest(test);
        underTest.save(result);

        // when
        Optional<Result> resultOptional = underTest.findByTestIdAndUserId(test.getId(), -1L);

        // then
        assertThat(resultOptional).isEmpty();
    }

    @Test
    void returnsEmptyOptionalOfResultWhenNonExistingTestIdAndNonExistingUserIdAreGiven() {
        // given
        Long nonExistingTestId = -1L;
        Long nonExistingUserId = -1L;

        // when
        Optional<Result> resultOptional = underTest.findByTestIdAndUserId(nonExistingTestId, nonExistingUserId);

        // then
        assertThat(resultOptional).isEmpty();
    }

    @Test
    void returnsPageOfResultsWithAtLeastOneResultWhenExistingUserIdIsGivenAndTheUserHasAtLeastOneResult() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        User user = SampleObjects.user();
        userRepository.save(user);
        Result result1 = SampleObjects.passResult();
        Result result2 = SampleObjects.failResult();
        result1.setUser(user);
        result2.setUser(user);
        underTest.save(result1);
        underTest.save(result2);

        // when
        Page<Result> results = underTest.findAllByUserId(user.getId(), pageable);

        // then
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.getContent()).containsExactlyInAnyOrder(result1, result2);
    }

    @Test
    void returnsEmptyPageWithNoResultsWhenNonExistingUserIdIsGivenOrTheUserHasNoResults() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Result> results = underTest.findAllByUserId(-1L, pageable);

        // then
        assertThat(results.getTotalElements()).isEqualTo(0);
    }

}