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
     *  1.Do All Users
     *  2.Unit tests
     *  3.Integration tests
     *  4.Work on admin privileges, a.k.a All Users and All Tests
     *
     *  To fix:
     *  - the timer on question-answer.html resets when page is refreshed or redirected to
     */

    public static void main(String[] args) {
        SpringApplication.run(TestingEzApplication.class, args);
    }

}
