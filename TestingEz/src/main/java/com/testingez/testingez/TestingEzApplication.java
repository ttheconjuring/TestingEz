package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class TestingEzApplication {

    /**
     * TODO:
     *  to 31.07:
     *  1.Result Details
     *  2.Test Details
     *  3.Question edit
     *  4.All Users
     *  ===========
     *  01.08:
     *  3.Unit tests
     *  4.Integration tests
     *  5.Work on admin privileges, a.k.a All Users and All Tests
     *
     *  To fix:
     *  - the timer on question-answer.html resets when page is refreshed or redirected to
     */

    public static void main(String[] args) {
        SpringApplication.run(TestingEzApplication.class, args);
    }

}
