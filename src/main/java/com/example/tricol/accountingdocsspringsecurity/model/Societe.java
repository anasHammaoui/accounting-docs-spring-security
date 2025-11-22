package com.example.tricol.accountingdocsspringsecurity.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Societe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String raisonSociale;

    @Column(nullable = false, unique = true)
    private String ice;

    private String adresse;

    private String telephone;

    @Column(nullable = false)
    private String emailContact;

    private LocalDateTime dateCreation = LocalDateTime.now();
}