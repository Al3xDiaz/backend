package com.chaoticteam.backend.auth.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login", description = "Login user")
public class LoginController {

    // Endpoint para login
    @PostMapping("/login")
    @Operation(
        summary = "Login user",
        description = "Login user",
        parameters = {
            @Parameter(
                name = "username",
                description = "Username",
                required = true,
                example = "admin"
            ),
            @Parameter(
                name = "password",
                description = "Password",
                required = true,
                example = "admin"
            )
        }
    )
    public String login() {
        // Retorna simplemente "OK"
        return "OK";
    }
}
