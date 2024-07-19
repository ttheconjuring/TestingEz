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
     *  1.Work on My Results
     *  2.Prevent a user to join a test twice
     *  3.Unit tests
     *  4.Integration tests
     *  5.Work on admin privileges, a.k.a All Users and All Tests
     *
     *  To fix:
     *  - the timer on question-answer.html resets when page is refreshed or redirected to
     *  - when I try to get back to an answered question multiple times (3-4 attempts)
     *      I get redirected to test-preview.html
     */

    public static void main(String[] args) {
        SpringApplication.run(TestingEzApplication.class, args);
    }

}
