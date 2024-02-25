package com.nnk.springboot;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/*System.out.println("user"+userService.getUserById(18));	
	User userCreated = new User();
	userCreated.setFullName("FullName");
	userCreated.setUsername("Username test updated");
	userCreated.setPassword("userTest1$");
	userCreated.setRole("USER");
	userCreated=userService.updateUserById(18,userCreated);
	System.out.println("userUpdatedTest"+userCreated);*/
	}
}
