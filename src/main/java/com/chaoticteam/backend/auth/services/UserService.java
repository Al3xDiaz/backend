package com.chaoticteam.backend.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaoticteam.backend.auth.entities.User;
import com.chaoticteam.backend.auth.repsository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> listUsers() {
        return repository.findAll();
    }
}