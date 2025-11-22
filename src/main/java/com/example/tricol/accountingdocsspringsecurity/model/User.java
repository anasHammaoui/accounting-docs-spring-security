package com.example.tricol.accountingdocsspringsecurity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.example.tricol.accountingdocsspringsecurity.enums.UserStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.Role;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe; // Société rattachée (nullable for comptable)

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    private LocalDateTime dateCreation = LocalDateTime.now();



}