package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.exceptions.custom.QuestionNotFoundException;
import com.testingez.testingez.exceptions.custom.ResultNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.exp.QuestionDetailsDTO;
import com.testingez.testingez.models.dtos.exp.ResponseToQuestionDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.QuestionEditDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Response;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.ResponseRepository;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.UserHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl underTest;

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private TestRepository testRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ResultRepository resultRepository;
    @Mock
    private ResponseRepository responseRepository;
    @Mock
    private UserHelperService userHelperService;
    @Captor
    private ArgumentCaptor<Question> questionCaptor;

    @Test
    void putDownShouldSuccessfullySaveQuestions() {
        // given
        Long testId = 1L;
        TestQuestionsDTO testQuestionsDTO = new TestQuestionsDTO();
        QuestionCreateDTO questionCreateDTO1 = new QuestionCreateDTO();
        QuestionCreateDTO questionCreateDTO2 = new QuestionCreateDTO();
        QuestionCreateDTO questionCreateDTO3 = new QuestionCreateDTO();
        testQuestionsDTO.setQuestions(List.of(
                questionCreateDTO1,
                questionCreateDTO2,
                questionCreateDTO3
        ));
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        Question question1 = SampleObjects.question();
        Question question2 = SampleObjects.question();
        Question question3 = SampleObjects.question();

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(modelMapper.map(questionCreateDTO1, Question.class)).thenReturn(question1);
        when(modelMapper.map(questionCreateDTO2, Question.class)).thenReturn(question2);
        when(modelMapper.map(questionCreateDTO3, Question.class)).thenReturn(question3);

        // when
        underTest.putDown(testQuestionsDTO, testId);

        // then
        verify(questionRepository).saveAndFlush(question1);
        verify(questionRepository).saveAndFlush(question2);
        verify(questionRepository).saveAndFlush(question3);
        assertThat(question1.getTest()).isEqualTo(test);
        assertThat(question1.getNumber()).isEqualTo(1);
        assertThat(question2.getTest()).isEqualTo(test);
        assertThat(question2.getNumber()).isEqualTo(2);
        assertThat(question3.getTest()).isEqualTo(test);
        assertThat(question3.getNumber()).isEqualTo(3);
    }

    @Test
    void putDownShouldThrowAnErrorWhenTheGivenTestIdIsInvalid() {
        // given
        Long invalidTestId = -1L;
        TestQuestionsDTO testQuestionsDTO = new TestQuestionsDTO();

        when(testRepository.findById(invalidTestId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.putDown(testQuestionsDTO, invalidTestId))
                .withMessageContaining("We couldn't test with id: " + invalidTestId);
    }

    @Test
    void getQuestionsCountOfTheTestShouldSuccessfullyReturnCount() {
        // given
        Long testId = 1L;
        int questionsCount = 2;
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        test.setQuestionsCount(questionsCount);

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        // when
        Integer questionsCountOfTheTest = underTest.getQuestionsCountOfTheTest(testId);

        // then
        assertThat(questionsCountOfTheTest).isEqualTo(questionsCount);
    }

    @Test
    void getQuestionsCountOfTheTestShouldThrowAnErrorWhenTheGivenTestIdIsInvalid() {
        // given
        Long invalidTestId = -1L;

        when(testRepository.findById(invalidTestId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getQuestionsCountOfTheTest(invalidTestId))
                .withMessageContaining("We couldn't test with id: " + invalidTestId);
    }

    @Test
    void fetchQuestionDataShouldSuccessfullyReturnTheCorrectData() {
        // given
        Long testId = 1L;
        Integer questionNumber = 1;
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        test.setQuestionsCount(5);
        test.setResponseTime(1);
        Question question = SampleObjects.question();
        question.setTest(test);
        question.setNumber(questionNumber);
        QuestionAnswerDTO questionAnswerDTO = SampleObjects.questionAnswerDTO();
        questionAnswerDTO.setTestId(testId);

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(questionRepository.findByTestIdAndNumber(testId, questionNumber)).thenReturn(Optional.of(question));
        when(modelMapper.map(question, QuestionAnswerDTO.class)).thenReturn(questionAnswerDTO);

        // when
        QuestionAnswerDTO result = underTest.fetchQuestionData(testId, questionNumber);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTestId()).isEqualTo(test.getId());
        assertThat(result.getQuestion()).isEqualTo(question.getQuestion());
        assertThat(result.getAnswer1()).isEqualTo(question.getAnswer1());
        assertThat(result.getAnswer2()).isEqualTo(question.getAnswer2());
        assertThat(result.getAnswer3()).isEqualTo(question.getAnswer3());
        assertThat(result.getAnswer4()).isEqualTo(question.getAnswer4());
        assertThat(result.getResponseTime()).isEqualTo(test.getResponseTime());
        assertThat(result.getNumber()).isEqualTo(questionNumber);
        assertThat(result.getPoints()).isEqualTo(question.getPoints());
    }

    @Test
    void fetchQuestionDataShouldThrowAnExceptionWhenTheGivenTestIdIsInvalid() {
        // given
        Long invalidTestId = -1L;

        when(testRepository.findById(invalidTestId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.fetchQuestionData(invalidTestId, 1))
                .withMessageContaining("We couldn't find test with id: " + invalidTestId);
    }

    @Test
    void fetchQuestionDataShouldThrowAnExceptionWhenTheQuestionCannotBeFound() {
        // given
        Long testId = 1L;
        Integer invalidQuestionNumber = -1;
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(questionRepository.findByTestIdAndNumber(testId, invalidQuestionNumber)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.fetchQuestionData(testId, invalidQuestionNumber))
                .withMessageContaining("Question(№" + invalidQuestionNumber + ") associated with test: " + testId +
                        "was not found!");
    }

    @Test
    void getAnsweredQuestionsDataShouldSuccessfullyReturnTheDesiredData() {
        // given
        Long resultId = 1L;
        Result result = SampleObjects.failResult();
        result.setId(resultId);
        Long testId = 1L;
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        result.setTest(test);
        Long userId = 1L;
        Response response1 = SampleObjects.correctResponse();
        Response response2 = SampleObjects.incorrectResponse();
        ResponseToQuestionDTO rtq1 = new ResponseToQuestionDTO();
        rtq1.setResponseText("random");
        ResponseToQuestionDTO rtq2 = new ResponseToQuestionDTO();
        rtq2.setResponseText("random");
        Question question1 = SampleObjects.question();
        Question question2 = SampleObjects.question();
        AnsweredQuestionDTO answeredQuestionDTO1 = SampleObjects.answeredQuestionDTO();
        AnsweredQuestionDTO answeredQuestionDTO2 = SampleObjects.answeredQuestionDTO();
        User user = SampleObjects.user();
        user.setId(userId);

        when(resultRepository.findById(resultId)).thenReturn(Optional.of(result));
        when(userHelperService.getLoggedUser()).thenReturn(user);
        when(modelMapper.map(response1, ResponseToQuestionDTO.class)).thenReturn(rtq1);
        when(modelMapper.map(response2, ResponseToQuestionDTO.class)).thenReturn(rtq2);
        when(responseRepository.findAllByTestIdAndUserId(testId, userId))
                .thenReturn(List.of(response1, response2));
        when(modelMapper.map(question1, AnsweredQuestionDTO.class)).thenReturn(answeredQuestionDTO1);
        when(modelMapper.map(question2, AnsweredQuestionDTO.class)).thenReturn(answeredQuestionDTO2);
        when(questionRepository.findAllByTestId(testId))
                .thenReturn(List.of(question1, question2));

        // when
        List<AnsweredQuestionDTO> answeredQuestionsData = underTest.getAnsweredQuestionsData(resultId);

        // then
        assertThat(answeredQuestionsData).hasSize(2);
        assertThat(answeredQuestionsData.getFirst()).isEqualTo(answeredQuestionDTO1);
        assertThat(answeredQuestionsData.get(1)).isEqualTo(answeredQuestionDTO2);
    }

    @Test
    void getAnsweredQuestionsDataShouldThrowAnExceptionWhenTheGivenResultIdIsInvalid() {
        // given
        Long invalidResultId = -1L;

        when(resultRepository.findById(invalidResultId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(ResultNotFoundException.class)
                .isThrownBy(()
                        // when
                        -> underTest.getAnsweredQuestionsData(invalidResultId))
                .withMessageContaining("We couldn't find result with id: " + invalidResultId);
    }

    @Test
    void getAnsweredQuestionsDataShouldThrownAnExceptionWhenTheResponsesSizeIsNotEqualToTheQuestionsSize() {
        // given
        Long resultId = 1L;
        Result result = SampleObjects.failResult();
        result.setId(resultId);
        Long testId = 1L;
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setId(testId);
        result.setTest(test);
        Long userId = 1L;
        Response response1 = SampleObjects.correctResponse();
        Response response2 = SampleObjects.incorrectResponse();
        ResponseToQuestionDTO rtq1 = new ResponseToQuestionDTO();
        rtq1.setResponseText("random");
        ResponseToQuestionDTO rtq2 = new ResponseToQuestionDTO();
        rtq2.setResponseText("random");
        Question question1 = SampleObjects.question();
        Question question2 = SampleObjects.question();
        Question question3 = SampleObjects.question();
        AnsweredQuestionDTO answeredQuestionDTO1 = SampleObjects.answeredQuestionDTO();
        AnsweredQuestionDTO answeredQuestionDTO2 = SampleObjects.answeredQuestionDTO();
        User user = SampleObjects.user();
        user.setId(userId);

        when(resultRepository.findById(resultId)).thenReturn(Optional.of(result));
        when(userHelperService.getLoggedUser()).thenReturn(user);
        when(modelMapper.map(response1, ResponseToQuestionDTO.class)).thenReturn(rtq1);
        when(modelMapper.map(response2, ResponseToQuestionDTO.class)).thenReturn(rtq2);
        when(responseRepository.findAllByTestIdAndUserId(testId, userId))
                .thenReturn(List.of(response1, response2));
        when(modelMapper.map(question1, AnsweredQuestionDTO.class)).thenReturn(answeredQuestionDTO1);
        when(modelMapper.map(question2, AnsweredQuestionDTO.class)).thenReturn(answeredQuestionDTO2);
        when(questionRepository.findAllByTestId(testId))
                .thenReturn(List.of(question1, question2, question3));

        // then
        assertThatExceptionOfType(ArrayStoreException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getAnsweredQuestionsData(resultId))
                .withMessageContaining("Responses count doesn't match the questions count!");
    }

    @Test
    void getQuestionOfATestShouldSuccessfullyReturnTheDesiredData() {
        // given
        Long testId = 1L;
        Question question1 = SampleObjects.question();
        Question question2 = SampleObjects.question();
        QuestionDetailsDTO qd1 = new QuestionDetailsDTO();
        QuestionDetailsDTO qd2 = new QuestionDetailsDTO();

        when(questionRepository.findAllByTestId(testId)).thenReturn(
                List.of(question1, question2)
        );
        when(modelMapper.map(question1, QuestionDetailsDTO.class)).thenReturn(qd1);
        when(modelMapper.map(question2, QuestionDetailsDTO.class)).thenReturn(qd2);

        // when
        List<QuestionDetailsDTO> questionsOfATest = underTest.getQuestionsOfATest(testId);

        // then
        assertThat(questionsOfATest).hasSize(2);
        assertThat(questionsOfATest.getFirst()).isEqualTo(qd1);
        assertThat(questionsOfATest.get(1)).isEqualTo(qd2);
    }

    @Test
    void fetchQuestionDataShouldSuccessfullyReturnTheDesiredData() {
        // given
        Long questionId = 1L;
        Question question = SampleObjects.question();
        question.setId(questionId);
        QuestionEditDTO questionEditDTO1 = new QuestionEditDTO();
        questionEditDTO1.setQuestion(question.getQuestion());
        questionEditDTO1.setId(questionId);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(modelMapper.map(question, QuestionEditDTO.class)).thenReturn(questionEditDTO1);

        // when
        QuestionEditDTO questionEditDTO = underTest.fetchQuestionData(questionId);

        // then
        assertThat(questionEditDTO).isNotNull();
        assertThat(questionEditDTO.getId()).isEqualTo(questionId);
        assertThat(questionEditDTO.getQuestion()).isEqualTo(question.getQuestion());
    }

    @Test
    void fetchQuestionDataShouldThrowAnExceptionWhenTheGivenQuestionIdIsInvalid() {
        // given
        Long invalidQuestionId = -1L;

        // then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.fetchQuestionData(invalidQuestionId))
                .withMessageContaining("We couldn't find question with id: " + invalidQuestionId);
    }

    @Test
    void editQuestionShouldSuccessfullyEditTheQuestion() {
        // given
        QuestionEditDTO questionEditDTO = SampleObjects.questionEditDTO();
        questionEditDTO.setId(1L);
        Question question = new Question();
        question.setId(1L);

        when(questionRepository.findById(questionEditDTO.getId())).thenReturn(Optional.of(question));

        // when
        underTest.editQuestion(questionEditDTO);

        // then
        verify(questionRepository).saveAndFlush(questionCaptor.capture());
        Question value = questionCaptor.getValue();
        assertThat(value.getQuestion()).isEqualTo(questionEditDTO.getQuestion());
        assertThat(value.getId()).isEqualTo(questionEditDTO.getId());
        assertThat(value.getAnswer1()).isEqualTo(questionEditDTO.getAnswer1());
        assertThat(value.getAnswer2()).isEqualTo(questionEditDTO.getAnswer2());
        assertThat(value.getAnswer3()).isEqualTo(questionEditDTO.getAnswer3());
        assertThat(value.getAnswer4()).isEqualTo(questionEditDTO.getAnswer4());
    }

    @Test
    void editQuestionShouldThrownAnExceptionWhenTheQuestionCannotBeFound() {
        // given
        Long invalidQuestionId = -1L;
        QuestionEditDTO questionEditDTO = SampleObjects.questionEditDTO();
        questionEditDTO.setId(invalidQuestionId);

        when(questionRepository.findById(invalidQuestionId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(QuestionNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.editQuestion(questionEditDTO))
                .withMessageContaining("We couldn't find question with id: " + invalidQuestionId);
    }


}