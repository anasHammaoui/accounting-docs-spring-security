package com.example.tricol.accountingdocsspringsecurity.model;

import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroPiece;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePiece type;

    private String categorieComptable;

    @Column(nullable = false)
    private LocalDateTime datePiece;

    @Column(nullable = false)
    private Double montant;

    private String fournisseur;

    @Column(nullable = false)
    private String fichierPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus statut = DocumentStatus.EN_ATTENTE;

    private LocalDateTime dateValidation;

    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe;

    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime dateModification = LocalDateTime.now();


}