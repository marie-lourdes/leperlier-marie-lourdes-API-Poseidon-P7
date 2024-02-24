package com.nnk.springboot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	UserService  userService ;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/*User userCreated = new User();
	userCreated.setFullName("FullName");
	userCreated.setUsername("Username");
	userCreated.setPassword("userTest1$");
	userCreated.setRole("USER");
	userCreated=userService.addUser(userCreated);
	System.out.println("userCreatedTest"+userCreated);*/
	}
}
