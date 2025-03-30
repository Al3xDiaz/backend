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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/commentaries")
@Tag(name = "commentary", description = "Module comentaries")
public class CommentaryController {

    @Autowired
    private CommentariesService service;

    @GetMapping
    @Operation(
        summary = "getCommentaries",
        description = "get commentaries",
        parameters = {
            @Parameter(
                name = "requestURL",
                description = "site origin",
                required = true,
                example = "localhost"
            )
        }
    )
    public ResponseEntity<List<CommentaryEntity>> getComentary(HttpServletRequest request){
        String siteUrl = request.getRequestURL().toString();
        List<CommentaryEntity> entities;
        entities = service.list(siteUrl);

        return ResponseEntity.ok(entities);
    }

}
