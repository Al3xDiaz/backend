package com.chaoticteam.backend.auth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaoticteam.backend.auth.entities.UserEntity;
import com.chaoticteam.backend.auth.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "all users", description = "Get all users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/all")
    @Operation(
        summary = "Get all users",
        description = "Get all users",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "OK",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserEntity.class)
                )
            )
        }
    )
    public List<UserEntity> getAll() {
        return service.listUsers();
    }
}
