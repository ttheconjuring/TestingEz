package com.testingez.testingez;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestingEzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingEzApplication.class, args);
	}

	// TODO:
	//   	 1.Create new template for /user/home endpoint
	// 	     2.Add pseudo-session, a.k.a. CurrentUser
	//		 3.Clear the templates by adding more fragments
	// 	 	 4.Add Unit Tests
	// 	     5.Find out how to display error for not matching password and confirmPassword
	//  	 6.Test if the login works with encoded passwords

}
