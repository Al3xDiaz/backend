package com.chaoticteam.backend.controllers.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    // Endpoint para login
    @PostMapping("/login")
    public String login() {
        // Retorna simplemente "OK"
        return "OK";
    }
}
