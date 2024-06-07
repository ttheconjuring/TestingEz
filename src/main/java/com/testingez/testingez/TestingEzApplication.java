package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingEzApplication.class, args);
	}

	// TODO:
	// 		 1.Add password encoding
	//   	 2.Create new template for /user/home endpoint
	// 	     3.Add pseudo-session, a.k.a. CurrentUser
	//		 4.Clear the templates by adding more fragments
	// 	 	 5.Add Unit Tests
	// 	     6.Find out how to display error for not matching password and confirmPassword

}
