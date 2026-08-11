package com.example.internmatch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Controller", description = "Health status endpoint for system baseline check")
public class HealthController {

    @GetMapping
    @Operation(summary = "Check system health status", description = "Returns a simple string indicating that the API is running")
    public ResponseEntity<String> getHealthStatus() {
        return ResponseEntity.ok("InternMatch API is running");
    }
}
