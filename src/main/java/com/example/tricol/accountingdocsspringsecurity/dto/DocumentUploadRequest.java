package com.example.tricol.accountingdocsspringsecurity.dto;

import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import lombok.Data;

@Data
public class DocumentUploadRequest {
    private String numeroPiece;
    private TypePiece type;
    private String categorieComptable;
    private String datePiece;
    private Double montant;
    private String fournisseur;
}
