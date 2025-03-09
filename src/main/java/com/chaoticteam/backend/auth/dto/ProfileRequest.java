package com.chaoticteam.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String firstName;
    private String lastName;
}
