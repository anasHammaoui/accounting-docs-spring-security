package com.example.tricol.accountingdocsspringsecurity.dto;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
}