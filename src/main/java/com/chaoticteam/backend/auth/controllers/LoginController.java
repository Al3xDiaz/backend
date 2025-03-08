package com.chaoticteam.backend.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chaoticteam.backend.auth.dto.AuthenticationRequest;
import com.chaoticteam.backend.auth.dto.AuthenticationSignUpRequest;
import com.chaoticteam.backend.auth.dto.AuthenticationResponse;
import com.chaoticteam.backend.auth.services.JwtService;
import com.chaoticteam.backend.auth.services.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login", description = "Login user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp service;

    @Autowired
    private JwtService jwtService;

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
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        try {
            System.out.println(request.getUsername());
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
            );

        } catch (AuthenticationException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));

    }

    // Endpoint para signup
    @PostMapping("/signup")
    @Operation(
        summary = "Signup user",
        description = "Signup user",
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
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody AuthenticationSignUpRequest request) {
        try {
            service.saveUser(request.getUsername(),request.getEmail(), request.getPassword());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
