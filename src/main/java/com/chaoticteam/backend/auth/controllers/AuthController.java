package com.chaoticteam.backend.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.chaoticteam.backend.auth.dto.AuthenticationRefresTokenRequest;
import com.chaoticteam.backend.auth.dto.AuthenticationRequest;
import com.chaoticteam.backend.auth.dto.AuthenticationSignUpRequest;
import com.chaoticteam.backend.auth.entities.UserEntity;
import com.chaoticteam.backend.auth.dto.AuthenticationResponse;
import com.chaoticteam.backend.auth.services.JwtService;
import com.chaoticteam.backend.auth.services.UserDetailsServiceImp;
import com.chaoticteam.backend.utils.HandleTransactionException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Module auth")
public class AuthController {

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
    @HandleTransactionException
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(request.getUsername(),jwtToken, jwtRefreshToken));
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
            ),
            @Parameter(
                name = "profile",
                description = "profile values (firstName & lastName)",
                required = true,
                example = "{ \"firstName\":\"John\",\"lastName\":\"Doo\"}"
            )
        }
    )
    @Transactional(
        rollbackOn = RuntimeException.class
    )
    @HandleTransactionException
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody AuthenticationSignUpRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(request.getPassword());

        // Guardar el perfil y el usuario dentro de la misma transacción
        service.saveUser(
            request.getUsername(),
            request.getEmail(),
            encodedPassword,
            request.getProfile().getFirstName(),
            request.getProfile().getLastName()
        );

        // Si todo va bien, generar el token JWT
        final UserDetails userDetails = service.loadUserByUsername(request.getUsername());
        final String jwtToken = jwtService.generateToken(userDetails);
        final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(request.getUsername(), jwtToken, jwtRefreshToken));
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
    @HandleTransactionException
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody AuthenticationRefresTokenRequest request) {
        if (!jwtService.validateToken(request.getRefreshToken())) {
            throw new RuntimeException("not valid jwt");
        }
        final String username = jwtService.getUsernameFromToken(request.getRefreshToken());
        final UserDetails userDetails = service.loadUserByUsername(username);
        final String jwtToken = jwtService.generateToken(userDetails);
        final String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(userDetails.getUsername(),jwtToken, jwtRefreshToken));
    }

    // Endpoint getUserData
    @GetMapping("/userdata")
    @Operation(
        summary = "GetUserData",
        description = "get user data"
    )
    @HandleTransactionException
    public ResponseEntity<UserEntity> getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity entity = service.getUserByUsername(username);
        return ResponseEntity.ok(entity);
    }
}
