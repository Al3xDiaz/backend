package com.chaoticteam.backend.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chaoticteam.backend.auth.entities.ProfileEntity;
import com.chaoticteam.backend.auth.entities.UserEntity;
import com.chaoticteam.backend.auth.repsository.UserRepository;
import com.chaoticteam.backend.auth.repsository.ProfileRepository;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProfileRepository profileRepository;

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

    public UserEntity getUserByUsername(String username) throws RuntimeException{
        return repository.findByUsername(username).orElseThrow();
    }

    public UserEntity saveUser(String username,String email, String password,String firstName, String lastName) throws RuntimeException {
        if (repository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        ProfileEntity profile = saveProfileEntity(
            firstName,
            lastName
        );

        UserEntity entity = new UserEntity(username, email, password,profile);
        entity.setRoleEntity(null);
        return repository.save(entity);
    }

    public ProfileEntity saveProfileEntity(String firstName,String lastName) {
        ProfileEntity entity = new ProfileEntity(firstName,lastName);
        return profileRepository.save(entity);
    }

}
