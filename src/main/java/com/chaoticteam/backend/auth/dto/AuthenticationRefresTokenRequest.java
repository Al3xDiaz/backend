package com.chaoticteam.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRefresTokenRequest {
    private String refreshToken;
}
