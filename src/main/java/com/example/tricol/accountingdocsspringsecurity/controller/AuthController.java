package com.example.tricol.accountingdocsspringsecurity.controller;

import com.example.tricol.accountingdocsspringsecurity.dto.AuthResponse;
import com.example.tricol.accountingdocsspringsecurity.dto.LoginRequest;
import com.example.tricol.accountingdocsspringsecurity.dto.RegisterRequest;
import com.example.tricol.accountingdocsspringsecurity.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}