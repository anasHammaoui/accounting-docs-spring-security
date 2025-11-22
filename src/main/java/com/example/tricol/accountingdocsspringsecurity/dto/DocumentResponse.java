package com.example.tricol.accountingdocsspringsecurity.dto;

import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentResponse {
    private Long id;
    private String numeroPiece;
    private TypePiece type;
    private String categorieComptable;
    private LocalDateTime datePiece;
    private Double montant;
    private String fournisseur;
    private DocumentStatus statut;
    private LocalDateTime dateValidation;
    private String commentaire;
    private String societeNom;
    private Long societeId;
    private LocalDateTime dateCreation;
}
