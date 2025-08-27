//package com.testingez.testingez.repositories;
//
//import com.testingez.testingez.SampleObjects;
//import com.testingez.testingez.models.entities.Question;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@DataJpaTest
//class QuestionRepositoryTest {
//
//    @Autowired
//    private QuestionRepository underTest;
//
//    @Autowired
//    private TestRepository testRepository;
//
//    @AfterEach
//    void tearDown() {
//        underTest.deleteAll();
//        testRepository.deleteAll();
//    }
//
//    @Test
//    void returnsNonEmptyOptionalOfQuestionWhenExistingTestIdAndExistingNumberAreGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        Optional<Question> optionalQuestion = underTest.findByTestIdAndNumber(test.getId(), 1);
//
//        // then
//        assertThat(optionalQuestion).isPresent();
//    }
//
//    @Test
//    void returnsNonEmptyOptionalOfQuestionWhenNonExistingTestIdAndExistingNumberAreGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        Optional<Question> optionalQuestion = underTest.findByTestIdAndNumber(-1L, 1);
//
//        // then
//        assertThat(optionalQuestion).isEmpty();
//    }
//
//    @Test
//    void returnsNonEmptyOptionalOfQuestionWhenExistingTestIdAndNonExistingNumberAreGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        Optional<Question> optionalQuestion = underTest.findByTestIdAndNumber(test.getId(), -1);
//
//        // then
//        assertThat(optionalQuestion).isEmpty();
//    }
//
//    @Test
//    void returnsNonEmptyOptionalOfQuestionWhenNonExistingTestIdAndNonExistingNumberAreGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        Optional<Question> optionalQuestion = underTest.findByTestIdAndNumber(-1L, -1);
//
//        // then
//        assertThat(optionalQuestion).isEmpty();
//    }
//
//    @Test
//    void returnsNotEmptyListOfQuestionsWhenExistingTestIdIsGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        List<Question> questionsList = underTest.findAllByTestId(test.getId());
//
//        // then
//        assertThat(questionsList).isNotEmpty()
//                .containsExactly(question);
//    }
//
//    @Test
//    void returnsEmptyListOfQuestionsWhenNonExistingTestIdIsGiven() {
//        // given
//        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
//        testRepository.save(test);
//        Question question = SampleObjects.question();
//        question.setTest(test);
//        underTest.save(question);
//
//        // when
//        List<Question> questionsList = underTest.findAllByTestId(-1L);
//
//        // then
//        assertThat(questionsList).isEmpty();
//    }
//}