package com.app.profile;

import com.app.profile.domain.model.User;
import com.app.profile.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ProfileApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProfileApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("john", "John", "White", "Delivery Manager", 31, LocalDate.now()));
		userRepository.save(new User("sherye", "Sherye", "Curley", "Nurse", 29, LocalDate.now()));
		userRepository.save(new User("sylvia","Sylvia", "Edmett","Farmer",30, LocalDate.now()));
	}
}
