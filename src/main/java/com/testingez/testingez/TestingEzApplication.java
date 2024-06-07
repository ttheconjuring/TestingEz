package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingEzApplication.class, args);
	}

	// TODO: 1.Remove the confirmPasswords logic in the controller
	//		 2.Separate the AuthenticationController into two different
	// 		 3.Add password encoding
	//   	 4.Create new template for /user/home endpoint
	// 	     5.Add pseudo-session, a.k.a. CurrentUser
	// 		 6.Remove the default browser behavior from the register and login forms
	//		 7.Clear the templates by adding more fragments

}
