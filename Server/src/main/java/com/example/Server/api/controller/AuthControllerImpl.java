package com.example.Server.api.controller;

import com.example.Server.api.dto.request.LoginRequest;
import com.example.Server.api.dto.request.RegisterRequest;
import com.example.Server.api.dto.response.AuthResponse;
import com.example.Server.dao.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class AuthControllerImpl {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/sso/google-url")
    public ResponseEntity<Map<String, String>> getGoogleAuthUrl() {
        return ResponseEntity.ok(Map.of("url", authService.getGoogleLoginUrl()));
    }

    @GetMapping("/sync")
    public ResponseEntity<Void> syncUser(@AuthenticationPrincipal Jwt jwt) {
        authService.syncUserFromJwt(jwt);
        return ResponseEntity.ok().build();
    }
}