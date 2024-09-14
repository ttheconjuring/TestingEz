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
     *   5.Make it possible to add new question to a test. - done
     *   6.Make it possible to delete a question from a test. - done
     *   7.Make it possible to delete a test - done
     *   8.Add functionality to the footer - done
     *   9.Add remember me option on login
     *   10.Add email account activation
     *   11.Write some tests (unit + integration)
     * */

}
