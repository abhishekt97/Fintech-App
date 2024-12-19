package com.abhishek.lendingengine.domain.service;

import com.abhishek.lendingengine.application.model.LoanRequest;
import com.abhishek.lendingengine.domain.repository.UserRepository;
import com.abhishek.lendingengine.domain.exception.UserNotFoundException;
import com.abhishek.lendingengine.domain.model.LoanApplication;
import com.abhishek.lendingengine.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest request, User borrower){
        Optional<User> optionalUser = userRepository.findById(borrower.getUsername());
        if (optionalUser.isPresent()){
            return new LoanApplication(borrower.getUsername(), request.getAmount(), optionalUser.get(),
                    request.getDaysToRepay(), request.getInterestRate());
        }else {
            throw new UserNotFoundException("User not found with user id :"+ borrower.getUsername());
        }

    }
}
