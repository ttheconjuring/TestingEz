package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingEzApplication.class, args);
	}

	// TODO:
	//	     1.Add validations to the questions creation + test different scenarios
	//		 2.Delete the last created test form the database when cancel button is clicked
	//   	 3.Create new template for /user/home endpoint
	// 	 	 4.Add Unit Tests
	//

}
