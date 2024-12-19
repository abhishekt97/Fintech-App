package com.abhishek.lendingengine.domain.service;

import com.abhishek.lendingengine.domain.exception.UserNotFoundException;
import com.abhishek.lendingengine.domain.model.Money;
import com.abhishek.lendingengine.domain.model.User;
import com.abhishek.lendingengine.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

    private final UserRepository userRepository;

    @Autowired
    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void topUpBalance(final Money money, String authToken){
        User user = getUser(authToken);
        user.topUp(money);

    }
    @Transactional
    public void withdrawBalance(final Money balance, String authToken){
        User user = getUser(authToken);
        user.withdraw(balance);

    }
//    private methods should b at bottom
    private User getUser(String authToken){
        User user = userRepository.findById(authToken)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user;
    }
}
