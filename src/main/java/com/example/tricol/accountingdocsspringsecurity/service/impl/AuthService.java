package com.example.tricol.accountingdocsspringsecurity.service.impl;

import com.example.tricol.accountingdocsspringsecurity.dto.AuthResponse;
import com.example.tricol.accountingdocsspringsecurity.dto.LoginRequest;
import com.example.tricol.accountingdocsspringsecurity.dto.RegisterRequest;
import com.example.tricol.accountingdocsspringsecurity.enums.UserStatus;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
import com.example.tricol.accountingdocsspringsecurity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);
        
        userRepository.save(user);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), 
                request.getPassword()
        );
        authentication = authenticationManager.authenticate(authentication);
        
        String token = jwtUtil.generateToken(authentication);
        return new AuthResponse(token, user.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        String token = jwtUtil.generateToken(authentication);
        return new AuthResponse(token, request.getEmail());
    }
}
