package com.example.internmatch.service;

import com.example.internmatch.dto.request.LoginRequest;
import com.example.internmatch.dto.request.RegisterRequest;
import com.example.internmatch.dto.response.AuthResponse;
import com.example.internmatch.entity.Role;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.exception.DuplicateResourceException;
import com.example.internmatch.repository.RoleRepository;
import com.example.internmatch.repository.UserRepository;
import com.example.internmatch.security.JwtService;
import com.example.internmatch.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("null")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, roleRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void registerShouldCreateUserAndReturnAuthResponse() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstName("Test")
                .lastName("User")
                .role(RoleName.ROLE_STUDENT)
                .build();

        Role role = Role.builder().id(1L).name(RoleName.ROLE_STUDENT).build();

        User savedUser = User.builder()
                .id(10L)
                .email("test@example.com")
                .password("encodedPassword")
                .firstName("Test")
                .lastName("User")
                .roles(Set.of(role))
                .isActive(true)
                .build();

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(roleRepository.findByName(RoleName.ROLE_STUDENT)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt.token.string");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwt.token.string", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("test@example.com", response.getUser().getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerShouldThrowDuplicateResourceExceptionWhenEmailExists() {
        RegisterRequest request = RegisterRequest.builder()
                .email("existing@example.com")
                .password("password")
                .build();

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void loginShouldAuthenticateAndReturnAuthResponse() {
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        Role role = Role.builder().id(1L).name(RoleName.ROLE_STUDENT).build();
        User user = User.builder()
                .id(10L)
                .email("test@example.com")
                .password("encodedPassword")
                .firstName("Test")
                .lastName("User")
                .roles(Set.of(role))
                .isActive(true)
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("test@example.com", "password"));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt.token.string");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt.token.string", response.getToken());
        assertEquals("test@example.com", response.getUser().getEmail());
    }
}
