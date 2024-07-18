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
     *  1.Create table results and implement all the necessary logic behind it - done
     *  2.Make it possible for users to see their own individual results on My Results page
     *  3.Unit tests
     *  4.Integration tests
     *  5.Work on admin privileges, a.k.a All Users and All Tests
     */

    public static void main(String[] args) {
        SpringApplication.run(TestingEzApplication.class, args);
    }

}
