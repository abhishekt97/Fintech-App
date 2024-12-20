package com.app.securityapp;

import com.app.securityapp.user.model.User;
import com.app.securityapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityappApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SecurityappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("john", "JohnsonK123"));
		userRepository.save(new User("sherye", "12345"));
		userRepository.save(new User("sylvia", "Pass12#"));
	}
}
