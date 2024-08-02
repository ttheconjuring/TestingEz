package com.testingez.testingez.repositories;

import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.TestStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestRepositoryTest {

    @Autowired
    private TestRepository underTest;

    @Autowired
    public UserRepository userRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void returnsNonEmptyOptionalOfTestWhenExistingCodeIsGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = sampleTest();
        underTest.save(test);

        // when
        Optional<com.testingez.testingez.models.entities.Test> optionalTest = underTest.findByCode(test.getCode());

        // then
        assertThat(optionalTest).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfTestWhenNonExistingCodeIsGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = sampleTest();
        underTest.save(test);

        // when
        Optional<com.testingez.testingez.models.entities.Test> optionalTest = underTest.findByCode("non-existing-code");

        // then
        assertThat(optionalTest).isEmpty();
    }

    @Test
    void returnsNonEmptyOptionalOfExistingTestWhenNullCodeIsGiven() {
        // given
        String code = null;

        // when
        Optional<com.testingez.testingez.models.entities.Test> optionalTest = underTest.findByCode(code);

        // then
        assertThat(optionalTest).isEmpty();
    }

    @Test
    void returnsEmptyPageWhenThereAreNoTestsInTheDatabase() {
        // given
        Long creatorId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<com.testingez.testingez.models.entities.Test> result = underTest.findAllByCreatorId(creatorId, pageable);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void returnsEmptyPageWhenThereAreNoTestsCreatedByTheGivenCreator() {
        // given
        Long creatorId = 2L; // 2L because the creator is 1L
        Pageable pageable = PageRequest.of(0, 10);
        com.testingez.testingez.models.entities.Test test = sampleTest();
        User creator = sampleUser();
        userRepository.save(creator);
        test.setCreator(creator);
        underTest.save(test);

        // when
        Page<com.testingez.testingez.models.entities.Test> result = underTest.findAllByCreatorId(creatorId, pageable);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void returnsPageWithSingleTestWhenTheCreatorHasOnlyOneTestCreated() {
        // given
        Long creatorId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        com.testingez.testingez.models.entities.Test test = sampleTest();
        User creator = sampleUser();
        userRepository.save(creator);
        test.setCreator(creator);
        underTest.save(test);

        // when
        Page<com.testingez.testingez.models.entities.Test> result = underTest.findAllByCreatorId(creatorId, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().getCreator().getId()).isEqualTo(creatorId);
    }

    @Test
    void returnsPageWithMultipleTestsWhenTheCreatorHasMoreThanOneTestsCreated() {
        // given
        Long creatorId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        com.testingez.testingez.models.entities.Test test1 = sampleTest();
        com.testingez.testingez.models.entities.Test test2 = sampleTest();
        test2.setCode("1*^6jM");
        User creator = sampleUser();
        userRepository.save(creator);
        test1.setCreator(creator);
        test2.setCreator(creator);
        underTest.saveAll(Arrays.asList(test1, test2));

        // when
        Page<com.testingez.testingez.models.entities.Test> result = underTest.findAllByCreatorId(creatorId, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        List<Long> creatorIds = result.getContent().stream()
                .map(test -> test.getCreator().getId())
                .toList();
        assertThat(creatorIds).containsOnly(creatorId);
    }

    private static com.testingez.testingez.models.entities.Test sampleTest() {
        com.testingez.testingez.models.entities.Test test = new com.testingez.testingez.models.entities.Test();
        test.setName("engineering");
        test.setDescription("random");
        test.setCode("3*#9sO");
        test.setResponseTime(1);
        test.setPassingScore(5);
        test.setQuestionsCount(10);
        test.setStatus(TestStatus.CLOSED);
        test.setDateCreated(LocalDateTime.now());
        test.setDateUpdated(LocalDateTime.now());
        return test;
    }

    private static User sampleUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        return user;
    }
}