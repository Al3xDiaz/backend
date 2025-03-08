package com.chaoticteam.backend.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaoticteam.backend.auth.dto.AuthenticationRefresTokenRequest;
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
@Tag(name = "Auth", description = "Module auth")
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
        final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken, jwtRefreshToken));

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
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(request.getPassword());
            service.saveUser(
                request.getUsername(),
                request.getEmail(),
                encodedPassword
            );
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken, jwtRefreshToken));
    }

    // Endpoint para refrescar el token
    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh token",
        description = "Refresh",
        parameters = {
            @Parameter(
                name = "refreshToken",
                description = "Refresh",
                required = true,
                example = "eyJhbGci"
            )
        }
    )
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody AuthenticationRefresTokenRequest request) {
        try {
            if (!jwtService.validateToken(request.getRefreshToken())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            final String username = jwtService.getUsernameFromToken(request.getRefreshToken());
            final UserDetails userDetails = service.loadUserByUsername(username);
            final String jwtToken = jwtService.generateToken(userDetails);
            final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken, jwtRefreshToken));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
