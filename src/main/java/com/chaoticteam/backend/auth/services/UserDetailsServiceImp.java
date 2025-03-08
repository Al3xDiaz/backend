package com.chaoticteam.backend.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.chaoticteam.backend.auth.entities.UserEntity;
import com.chaoticteam.backend.auth.repsository.UserRepository;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username)
            .orElse(null);
        String authorities = "without role";
        if (user.getRoleEntity() != null) {
            authorities = user.getRoleEntity().getName();
        }
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(authorities)
            .build();
    }

    public UserEntity saveUser(String username,String email, String password) {
        if (repository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity entity = new UserEntity(username, email, password);
        entity.setRoleEntity(null);
        return repository.save(entity);
    }

}
