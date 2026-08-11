package com.example.internmatch.controller;

import com.example.internmatch.dto.request.LoginRequest;
import com.example.internmatch.dto.request.RegisterRequest;
import com.example.internmatch.dto.response.AuthResponse;
import com.example.internmatch.dto.response.UserResponseDto;
import com.example.internmatch.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Kullanıcı Kayıt, Giriş ve Oturum İşlemleri")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Yeni Kullanıcı Kaydı", description = "Sisteme yeni bir kullanıcı kaydeder ve JWT token döndürür.")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Kullanıcı Girişi", description = "Email ve şifre ile giriş yaparak JWT token elde eder.")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Mevcut Kullanıcı Bilgileri", description = "JWT token ile kimliği doğrulanmış kullanıcının detaylarını döndürür.")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto response = authService.getCurrentUser();
        return ResponseEntity.ok(response);
    }
}
