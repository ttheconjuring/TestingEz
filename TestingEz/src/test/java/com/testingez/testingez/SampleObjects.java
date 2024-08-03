package com.testingez.testingez;

import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.ResultStatus;
import com.testingez.testingez.models.enums.TestStatus;

import java.time.LocalDateTime;

public class SampleObjects {

    private SampleObjects() {
    }

    public static Test sampleTest() {
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

    public static User sampleUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstName("Petar");
        user.setLastName("Kele-sho");
        user.setEmail("pesho@aes.vas");
        user.setPhone("048-581-5853");
        return user;
    }

    public static Result sampleResult() {
        Result result = new Result();
        result.setPoints(5);
        result.setResult("5/10");
        result.setCompletedAt(LocalDateTime.now());
        result.setStatus(ResultStatus.PASS);
        return result;
    }

}
