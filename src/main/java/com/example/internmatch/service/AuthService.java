package com.example.internmatch.service;

import com.example.internmatch.dto.request.LoginRequest;
import com.example.internmatch.dto.request.RegisterRequest;
import com.example.internmatch.dto.response.AuthResponse;
import com.example.internmatch.dto.response.UserResponseDto;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponseDto getCurrentUser();
}
