package com.testingez.testingez;

import com.testingez.testingez.models.dtos.UserProfileDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.dtos.imp.UserSignUpDTO;
import com.testingez.testingez.models.entities.*;
import com.testingez.testingez.models.enums.ResultStatus;
import com.testingez.testingez.models.enums.TestStatus;
import com.testingez.testingez.models.enums.UserRole;

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

    public static Result result() {
        Result result = new Result();
        result.setPoints(5);
        result.setResult("5/10");
        result.setCompletedAt(LocalDateTime.now());
        result.setStatus(ResultStatus.PASS);
        return result;
    }

    public static Response response() {
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

}
