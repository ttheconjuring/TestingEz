package com.testingez.testingez.repositories;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ResponseRepositoryTest {

    @Autowired
    private ResponseRepository underTest;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        testRepository.deleteAll();
        userRepository.deleteAll();
        questionRepository.deleteAll();
    }

    @Test
    void returnsNonEmptyListOfResponsesWhenExistingTestIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setTest(test);
        underTest.save(response);

        // when
        List<Response> responsesList = underTest.findAllByTestIdAndUserId(test.getId(), user.getId());

        // then
        assertThat(responsesList).hasSize(1);
    }

    @Test
    void returnsEmptyListOfResponsesWhenNonExistingTestIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setTest(test);
        underTest.save(response);

        // when
        List<Response> responsesList = underTest.findAllByTestIdAndUserId(-1L, user.getId());

        // then
        assertThat(responsesList).isEmpty();
    }

    @Test
    void returnsEmptyListOfResponsesWhenExistingTestIdAndNonExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setTest(test);
        underTest.save(response);

        // when
        List<Response> responsesList = underTest.findAllByTestIdAndUserId(test.getId(), -1L);

        // then
        assertThat(responsesList).isEmpty();
    }

    @Test
    void returnsEmptyListOfResponsesWhenNonExistingTestIdAndNonExistingUserIdAreGiven() {
        // given
        Long nonExistingTestId = -1L;
        Long nonExistingUserId = -1L;

        // when
        List<Response> responsesList = underTest.findAllByTestIdAndUserId(nonExistingTestId, nonExistingUserId);

        // then
        assertThat(responsesList).isEmpty();
    }

    @Test
        // + + +
    void returnsNonEmptyOptionalOfResponseWhenExistingTestIdAndExistingQuestionIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(test.getId(), question.getId(), user.getId());

        // then
        assertThat(optionalResponse).isPresent();
    }

    @Test
        // - + +
    void returnsEmptyOptionalOfResponseWhenNonExistingTestIdAndExistingQuestionIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(-1L, question.getId(), user.getId());

        // then
        assertThat(optionalResponse).isEmpty();
    }

    @Test
        // + - +
    void returnsEmptyOptionalOfResponseWhenExistingTestIdAndNonExistingQuestionIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(test.getId(), -1L, user.getId());

        // then
        assertThat(optionalResponse).isEmpty();
    }

    @Test
        // + + -
    void returnsEmptyOptionalOfResponseWhenExistingTestIdAndExistingQuestionIdAndNonExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(test.getId(), question.getId(), -1L);

        // then
        assertThat(optionalResponse).isEmpty();

    }

    @Test
        // - - +
    void returnsEmptyOptionalOfResponseWhenNonExistingTestIdAndNonExistingQuestionIdAndExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(-1L, -1L, user.getId());

        // then
        assertThat(optionalResponse).isEmpty();
    }

    @Test
        // + - -
    void returnsEmptyOptionalOfResponseWhenExistingTestIdAndNonExistingQuestionIdAndNonExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(test.getId(), -1L, -1L);

        // then
        assertThat(optionalResponse).isEmpty();
    }

    @Test
        // - + -
    void returnsEmptyOptionalOfResponseWhenNonExistingTestIdAndExistingQuestionIdAndNonExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(-1L, question.getId(), -1L);

        // then
        assertThat(optionalResponse).isEmpty();
    }

    @Test
        // - - -
    void returnsEmptyOptionalOfResponseWhenNonExistingTestIdAndNonExistingQuestionIdAndNonExistingUserIdAreGiven() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        testRepository.save(test);
        Question question = SampleObjects.question();
        questionRepository.save(question);
        User user = SampleObjects.user();
        userRepository.save(user);
        Response response = SampleObjects.response();
        response.setUser(user);
        response.setQuestion(question);
        response.setTest(test);
        underTest.save(response);

        // when
        Optional<Response> optionalResponse = underTest.findByTestIdAndQuestionIdAndUserId(-1L, -1L, -1L);

        // then
        assertThat(optionalResponse).isEmpty();
    }

}