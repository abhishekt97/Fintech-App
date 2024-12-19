package com.app.profile.application;

import com.app.profile.domain.model.User;
import com.app.profile.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public void addUser(@RequestBody final User user){
        user.setRegisterdSince(LocalDate.now());
        userRepository.save(user);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody User user){
        userRepository.save(user);
    }

}
