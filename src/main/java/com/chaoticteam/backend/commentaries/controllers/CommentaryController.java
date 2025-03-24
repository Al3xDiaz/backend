package com.chaoticteam.backend.commentaries.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaoticteam.backend.commentaries.entities.CommentaryEntity;
import com.chaoticteam.backend.commentaries.services.CommentariesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/commentaries")
@Tag(name = "Auth", description = "Module auth")
public class CommentaryController {

    @Autowired
    private CommentariesService service;

    @GetMapping
    @Operation(
        summary = "getCommentaries",
        description = "get commentaries"
    )
    public ResponseEntity<List<CommentaryEntity>> getComentary(){
        List<CommentaryEntity> entities;
        entities = service.list();

        return ResponseEntity.ok(entities);
    }

}
