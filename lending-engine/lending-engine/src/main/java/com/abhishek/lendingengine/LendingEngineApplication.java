package com.abhishek.lendingengine;

import com.abhishek.lendingengine.domain.model.Balance;
import com.abhishek.lendingengine.domain.model.Currency;
import com.abhishek.lendingengine.domain.model.Money;
import com.abhishek.lendingengine.domain.model.User;
import com.abhishek.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LendingEngineApplication implements CommandLineRunner {


	private final UserRepository  userRepository;

	public LendingEngineApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LendingEngineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User john = new User("john","John", "Urch", 25,"Unemployed", new Balance());
		User sherye = new User("sherye","Sherye", "Curley", 29,"Nurse", new Balance());
		User sylvia = new User("sylvia","Sylvia", "Edmett", 30,"Farmer", new Balance());
		john.topUp(new Money(Currency.INR, 1000));
		sherye.topUp(new Money(Currency.INR, 1000));
		userRepository.save(john);
		userRepository.save(sherye);
		userRepository.save(sylvia);

	}
}
