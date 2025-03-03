package com.chaoticteam.backend.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaoticteam.backend.auth.entities.UserEntity;
import com.chaoticteam.backend.auth.repsository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserEntity saveUser(UserEntity user) {
        return repository.save(user);
    }

    public List<UserEntity> listUsers() {
        return repository.findAll();
    }
}