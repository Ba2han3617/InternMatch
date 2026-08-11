package com.example.internmatch.controller;

import com.example.internmatch.config.SecurityConfig;
import com.example.internmatch.dto.request.LoginRequest;
import com.example.internmatch.dto.request.RegisterRequest;
import com.example.internmatch.dto.response.AuthResponse;
import com.example.internmatch.dto.response.UserResponseDto;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.internmatch.security.JwtAuthenticationFilter;

@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void registerShouldReturnCreatedStatusAndAuthResponse() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("student@example.com")
                .password("password123")
                .firstName("Ali")
                .lastName("Yilmaz")
                .role(RoleName.ROLE_STUDENT)
                .build();

        UserResponseDto userDto = UserResponseDto.builder()
                .id(1L)
                .email("student@example.com")
                .firstName("Ali")
                .lastName("Yilmaz")
                .roles(Set.of("ROLE_STUDENT"))
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .token("mock-jwt-token")
                .tokenType("Bearer")
                .user(userDto)
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.user.email").value("student@example.com"));
    }

    @Test
    void loginShouldReturnOkStatusAndAuthResponse() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("student@example.com")
                .password("password123")
                .build();

        UserResponseDto userDto = UserResponseDto.builder()
                .id(1L)
                .email("student@example.com")
                .firstName("Ali")
                .lastName("Yilmaz")
                .roles(Set.of("ROLE_STUDENT"))
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .token("mock-jwt-token")
                .tokenType("Bearer")
                .user(userDto)
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.user.email").value("student@example.com"));
    }

    @Test
    @WithMockUser
    void getCurrentUserShouldReturnUserResponseDto() throws Exception {
        UserResponseDto userDto = UserResponseDto.builder()
                .id(1L)
                .email("student@example.com")
                .firstName("Ali")
                .lastName("Yilmaz")
                .roles(Set.of("ROLE_STUDENT"))
                .build();

        when(authService.getCurrentUser()).thenReturn(userDto);

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("student@example.com"))
                .andExpect(jsonPath("$.firstName").value("Ali"));
    }
}
