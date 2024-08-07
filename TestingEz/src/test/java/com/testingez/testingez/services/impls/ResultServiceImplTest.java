package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.exceptions.custom.UserNotFoundException;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.repositories.ResponseRepository;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.services.UserHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceImplTest {

    @InjectMocks
    ResultServiceImpl underTest;

    @Mock
    private ResultRepository resultRepository;
    @Mock
    private ResponseRepository responseRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserHelperService userHelperService;

    @Test
    void calculateResultShouldCalculatePositiveResult() {
        // given
        Long testId = 1L;
        Long userId = 1L;
        Question question = SampleObjects.question();
        Response response1 = SampleObjects.correctResponse();
        response1.setQuestion(question);
        Response response2 = SampleObjects.correctResponse();
        response2.setQuestion(question);
        Response response3 = SampleObjects.correctResponse();
        response3.setQuestion(question);
        Response response4 = SampleObjects.correctResponse();
        response4.setQuestion(question);
        Response response5 = SampleObjects.correctResponse();
        response5.setQuestion(question);
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        User user = SampleObjects.user();
        user.setId(userId);
        Result passResult = SampleObjects.passResult();
        passResult.setId(1L);

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(responseRepository.findAllByTestIdAndUserId(testId, userId))
                .thenReturn(List.of(response1, response2, response3, response4, response5));
        when(resultRepository.saveAndFlush(any(Result.class))).thenReturn(passResult);

        // when
        Result calculatedResult = underTest.calculateResult(testId, userId);

        // then
        assertThat(calculatedResult).isNotNull();
        assertThat(calculatedResult.getId()).isEqualTo(passResult.getId());
        assertThat(calculatedResult.getTest()).isEqualTo(passResult.getTest());
        assertThat(calculatedResult.getUser()).isEqualTo(passResult.getUser());
        assertThat(calculatedResult.getPoints()).isEqualTo(passResult.getPoints());
        assertThat(calculatedResult.getResult()).isEqualTo(passResult.getResult());
        assertThat(calculatedResult.getStatus()).isEqualTo(passResult.getStatus());
    }

    @Test
    void calculateResultShouldCalculateNegativeResult() {
        // given
        Long testId = 1L;
        Long userId = 1L;
        Response response1 = SampleObjects.incorrectResponse();
        Response response2 = SampleObjects.incorrectResponse();
        Response response3 = SampleObjects.incorrectResponse();
        Response response4 = SampleObjects.incorrectResponse();
        Response response5 = SampleObjects.incorrectResponse();
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        User user = SampleObjects.user();
        user.setId(userId);
        Result failResult = SampleObjects.failResult();
        failResult.setId(1L);

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(responseRepository.findAllByTestIdAndUserId(testId, userId))
                .thenReturn(List.of(response1, response2, response3, response4, response5));
        when(resultRepository.saveAndFlush(any(Result.class))).thenReturn(failResult);

        // when
        Result calculatedResult = underTest.calculateResult(testId, userId);

        // then
        assertThat(calculatedResult).isNotNull();
        assertThat(calculatedResult.getId()).isEqualTo(failResult.getId());
        assertThat(calculatedResult.getTest()).isEqualTo(failResult.getTest());
        assertThat(calculatedResult.getUser()).isEqualTo(failResult.getUser());
        assertThat(calculatedResult.getPoints()).isEqualTo(failResult.getPoints());
        assertThat(calculatedResult.getResult()).isEqualTo(failResult.getResult());
        assertThat(calculatedResult.getStatus()).isEqualTo(failResult.getStatus());
    }

    @Test
    void calculateResultShouldThrowAnExceptionWhenTestIdIsInvalid() {
        // given
        Long invalidTestId = -1L;

        when(testRepository.findById(invalidTestId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.calculateResult(invalidTestId, anyLong()))
                .withMessageContaining("We couldn't find test with id: " + invalidTestId);
    }

    @Test
    void calculateResultShouldThrowAnExceptionWhenTheUserIdIsInvalid() {
        // given
        Long invalidUserId = -1L;

        when(testRepository.findById(anyLong())).thenReturn(Optional.of(SampleObjects.test()));
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.calculateResult(anyLong(), invalidUserId))
                .withMessageContaining("We couldn't find user with id: " + invalidUserId);
    }


}