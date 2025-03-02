package com.chaoticteam.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "Health check")
public class HealthController {

    @GetMapping("/health")
    @Operation(
        summary = "Health check",
        description = "Health check"
    )
    public Void healthVoid() {
        return null;
    }
}
