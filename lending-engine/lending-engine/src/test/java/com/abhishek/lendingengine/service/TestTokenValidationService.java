package com.abhishek.lendingengine.service;

import com.abhishek.lendingengine.domain.exception.UserNotFoundException;
import com.abhishek.lendingengine.domain.model.User;
import com.abhishek.lendingengine.domain.repository.UserRepository;
import com.abhishek.lendingengine.application.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;



@Primary
@Profile("test")
@Component
public class TestTokenValidationService implements TokenValidationService {

    private final UserRepository userRepository;

    @Autowired
    public TestTokenValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User validateTokenAndGetUser(String token) {
        return userRepository.findById(token).orElseThrow(()-> new UserNotFoundException("user not found"));
    }
}
