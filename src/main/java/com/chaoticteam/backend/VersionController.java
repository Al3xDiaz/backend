package com.chaoticteam.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chaoticteam.backend.utils.HandleTransactionException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/")
@Tag(name = "basicconfig", description = "Module basic config")
public class VersionController {

    @Value("${config.version}")
    private String version;

    @GetMapping("/version")
    @Operation(
        summary = "API version",
        description = "return api version"
    )
    @HandleTransactionException
    public ResponseEntity<String> getVersion(){
        return ResponseEntity.ok(version);
    }

}
