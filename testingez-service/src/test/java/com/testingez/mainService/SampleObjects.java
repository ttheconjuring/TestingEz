package com.testingez.mainService;

import com.testingez.mainService.model.dtos.UserProfileDTO;
import com.testingez.mainService.model.dtos.exp.*;
import com.testingez.mainService.model.dtos.imp.QuestionEditDTO;
import com.testingez.mainService.model.dtos.imp.TestCreateDTO;
import com.testingez.mainService.model.dtos.imp.UserSignUpDTO;
import com.testingez.mainService.model.entities.*;
import com.testingez.mainService.model.enums.ResultStatus;
import com.testingez.mainService.model.enums.TestStatus;
import com.testingez.mainService.model.enums.UserRole;

import java.time.LocalDateTime;

public class SampleObjects {

    private SampleObjects() {
    }

    public static Test test() {
        Test test = new Test();
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

    public static User user() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        return user;
    }

    public static Result passResult() {
        Result result = new Result();
        result.setPoints(10);
        result.setResult("10/10");
        result.setCompletedAt(LocalDateTime.now());
        result.setStatus(ResultStatus.PASS);
        return result;
    }

    public static Result failResult() {
        Result result = new Result();
        result.setPoints(0);
        result.setResult("0/10");
        result.setCompletedAt(LocalDateTime.now());
        result.setStatus(ResultStatus.FAIL);
        return result;
    }

    public static Response correctResponse() {
        Response response = new Response();
        response.setResponseText("random");
        response.setIsCorrect(true);
        response.setSubmittedOn(LocalDateTime.now());
        return response;
    }

    public static Response incorrectResponse() {
        Response response = new Response();
        response.setResponseText("random");
        response.setIsCorrect(false);
        response.setSubmittedOn(LocalDateTime.now());
        return response;
    }

    public static Question question() {
        Question question = new Question();
        question.setQuestion("q?");
        question.setAnswer1("a1");
        question.setAnswer2("a2");
        question.setAnswer3("a3");
        question.setAnswer4("a4");
        question.setCorrectAnswer("a3");
        question.setPoints(2);
        question.setNumber(1);
        return question;
    }

    public static UserSignUpDTO userSignUpDTO() {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setUsername("username");
        userSignUpDTO.setPassword("password");
        userSignUpDTO.setConfirmPassword("password");
        userSignUpDTO.setFirstName("Petar");
        userSignUpDTO.setLastName("Kele-sho");
        userSignUpDTO.setEmail("pesho@aes.vas");
        userSignUpDTO.setPhone("048-581-5853");
        return userSignUpDTO;
    }

    public static Role adminRole() {
        Role role = new Role();
        role.setId(1L);
        role.setRole(UserRole.ADMINISTRATOR);
        return role;
    }

    public static Role standardRole() {
        Role role = new Role();
        role.setId(2L);
        role.setRole(UserRole.STANDARD);
        return role;
    }

    public static UserProfileDTO userProfileDTO() {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUsername("username");
        userProfileDTO.setFirstName("Petar");
        userProfileDTO.setLastName("Kele-sho");
        userProfileDTO.setEmail("pesho@aes.vas");
        userProfileDTO.setPhone("048-581-5853");
        userProfileDTO.setRole("STANDARD");
        return userProfileDTO;
    }

    public static TestCreateDTO testCreateDTO() {
        TestCreateDTO testCreateDTO = new TestCreateDTO();
        testCreateDTO.setName("engineering");
        testCreateDTO.setDescription("random");
        testCreateDTO.setCode("3*#9sO");
        testCreateDTO.setResponseTime(1);
        testCreateDTO.setPassingScore(5);
        testCreateDTO.setQuestionsCount(10);
        testCreateDTO.setStatus("closed");
        return testCreateDTO;
    }

    public static TestPreviewDTO testPreviewDTO() {
        TestPreviewDTO testPreviewDTO = new TestPreviewDTO();
        testPreviewDTO.setName("engineering");
        testPreviewDTO.setDescription("random");
        testPreviewDTO.setResponseTime(1);
        testPreviewDTO.setPassingScore(5);
        testPreviewDTO.setQuestionsCount(10);
        testPreviewDTO.setStatus("CLOSED");
        testPreviewDTO.setCreatorUsername("username");
        return testPreviewDTO;
    }

    public static TestDetailsDTO testDetailsDTO() {
        TestDetailsDTO testDetailsDTO = new TestDetailsDTO();
        testDetailsDTO.setName("engineering");
        testDetailsDTO.setDescription("random");
        testDetailsDTO.setCode("3*#9sO");
        testDetailsDTO.setResponseTime(1);
        testDetailsDTO.setPassingScore(5);
        testDetailsDTO.setQuestionsCount(10);
        testDetailsDTO.setStatus("CLOSED");
        testDetailsDTO.setDateCreated(LocalDateTime.now());
        testDetailsDTO.setDateUpdated(LocalDateTime.now());
        testDetailsDTO.setCreatorUsername("username");
        return testDetailsDTO;
    }

    public static ResultSummaryDTO passResultSummaryDTO() {
        ResultSummaryDTO resultSummaryDTO = new ResultSummaryDTO();
        resultSummaryDTO.setPoints(10);
        resultSummaryDTO.setResult("5/5");
        resultSummaryDTO.setStatus("PASS");
        return resultSummaryDTO;
    }

    public static ResultSummaryDTO failResultSummaryDTO() {
        ResultSummaryDTO resultSummaryDTO = new ResultSummaryDTO();
        resultSummaryDTO.setPoints(0);
        resultSummaryDTO.setResult("0/5");
        resultSummaryDTO.setStatus("FAIL");
        return resultSummaryDTO;
    }

    public static ResultDetailsDTO failResultDetailsDTO() {
        ResultDetailsDTO resultDetailsDTO = new ResultDetailsDTO();
        resultDetailsDTO.setTestName("engineering");
        resultDetailsDTO.setStatus("FAIL");
        resultDetailsDTO.setResult("0/5");
        resultDetailsDTO.setPoints(0);
        resultDetailsDTO.setCompletedAt(LocalDateTime.parse("2024-07-31T16:46:45.735197"));
        return resultDetailsDTO;
    }

    public static ResultDetailsDTO passResultDetailsDTO() {
        ResultDetailsDTO resultDetailsDTO = new ResultDetailsDTO();
        resultDetailsDTO.setTestName("engineering");
        resultDetailsDTO.setStatus("PASS");
        resultDetailsDTO.setResult("5/5");
        resultDetailsDTO.setPoints(10);
        resultDetailsDTO.setCompletedAt(LocalDateTime.parse("2024-07-31T16:46:45.735197"));
        return resultDetailsDTO;
    }

    public static TestPeekDTO testPeekDTO() {
        TestPeekDTO testPeekDTO = new TestPeekDTO();
        testPeekDTO.setName("engineering");
        testPeekDTO.setStatus("OPEN");
        testPeekDTO.setDateUpdated(LocalDateTime.parse("2024-07-31T16:46:45.735197"));
        return testPeekDTO;
    }

    public static ResultPeekDTO passResultPeekDTO() {
        ResultPeekDTO resultPeekDTO = new ResultPeekDTO();
        resultPeekDTO.setTestName("engineering");
        resultPeekDTO.setCompletedAt(LocalDateTime.parse("2024-07-31T16:46:45.735197"));
        resultPeekDTO.setStatus("PASS");
        return resultPeekDTO;
    }

    public static ResultPeekDTO failResultPeekDTO() {
        ResultPeekDTO resultPeekDTO = new ResultPeekDTO();
        resultPeekDTO.setTestName("engineering");
        resultPeekDTO.setCompletedAt(LocalDateTime.parse("2024-07-31T16:46:45.735197"));
        resultPeekDTO.setStatus("FAIl");
        return resultPeekDTO;
    }

    public static QuestionAnswerDTO questionAnswerDTO() {
        QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
        questionAnswerDTO.setQuestion("q?");
        questionAnswerDTO.setAnswer1("a1");
        questionAnswerDTO.setAnswer2("a2");
        questionAnswerDTO.setAnswer3("a3");
        questionAnswerDTO.setAnswer4("a4");
        questionAnswerDTO.setPoints(2);
        questionAnswerDTO.setNumber(1);
        questionAnswerDTO.setResponseTime(1);
        return questionAnswerDTO;
    }

    public static AnsweredQuestionDTO answeredQuestionDTO() {
        AnsweredQuestionDTO answeredQuestionDTO = new AnsweredQuestionDTO();
        answeredQuestionDTO.setQuestion("q?");
        answeredQuestionDTO.setAnswer1("a1");
        answeredQuestionDTO.setAnswer2("a2");
        answeredQuestionDTO.setAnswer3("a3");
        answeredQuestionDTO.setAnswer4("a4");
        answeredQuestionDTO.setCorrectAnswer("a4");
        answeredQuestionDTO.setGivenAnswer("a1");
        answeredQuestionDTO.setPoints(2);
        answeredQuestionDTO.setNumber(1);
        return answeredQuestionDTO;
    }

    public static QuestionEditDTO questionEditDTO() {
        QuestionEditDTO questionEditDTO = new QuestionEditDTO();
        questionEditDTO.setQuestion("q?");
        questionEditDTO.setAnswer1("a1");
        questionEditDTO.setAnswer2("a2");
        questionEditDTO.setAnswer3("a3");
        questionEditDTO.setAnswer4("a4");
        questionEditDTO.setCorrectAnswer("a4");
        questionEditDTO.setNumber(1);
        return questionEditDTO;
    }

}
