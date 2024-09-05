package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class TestingEzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingEzApplication.class, args);
    }

    /*
     * TODO:
     *   1.Implement logic behind button 'Delete Account'. - done
     *   2.Implement logic behind button 'change' (avatar). - done
     *   3.Find out why login is required after username change.
     *   4.Implement logic behind link 'All Users'
     *   5.Make it possible to change the response time and
     *      passing score of a test and also to add a new questions.
     *   6.Make it possible to delete a test
     *   7.Add functionality to the footer
     *   8.Add remember me option on login
     *   9.Add email account activation
     *   10.Write some tests (unit + integration)
     * */

}
