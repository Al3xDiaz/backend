package com.chaoticteam.backend.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationSignUpRequest {
    private String username;
    private String email;
    private String password;
}