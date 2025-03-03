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
            .orElseThrow(()-> new UsernameNotFoundException("username: " + username + " not found"));

        String authorities = user.getRoleEntity().getName();

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .roles(authorities)
            .build();
    }

}
