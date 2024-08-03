package com.testingez.testingez.repositories;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        underTest.save(test);

        // when
        Optional<com.testingez.testingez.models.entities.Test> optionalTest = underTest.findByCode(test.getCode());

        // then
        assertThat(optionalTest).isPresent();
    }

    @Test
    void returnsEmptyOptionalOfTestWhenNonExistingCodeIsGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
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
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        User creator = SampleObjects.user();
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
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        User creator = SampleObjects.user();
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
        com.testingez.testingez.models.entities.Test test1 = SampleObjects.test();
        com.testingez.testingez.models.entities.Test test2 = SampleObjects.test();
        test2.setCode("1*^6jM");
        User creator = SampleObjects.user();
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

}