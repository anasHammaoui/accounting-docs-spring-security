package com.example.tricol.accountingdocsspringsecurity.model;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
