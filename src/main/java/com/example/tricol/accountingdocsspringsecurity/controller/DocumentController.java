package com.example.tricol.accountingdocsspringsecurity.controller;

import com.example.tricol.accountingdocsspringsecurity.dto.DocumentRejectionRequest;
import com.example.tricol.accountingdocsspringsecurity.dto.DocumentResponse;
import com.example.tricol.accountingdocsspringsecurity.dto.DocumentUploadRequest;
import com.example.tricol.accountingdocsspringsecurity.dto.DocumentValidationRequest;
import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import com.example.tricol.accountingdocsspringsecurity.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("numeroPiece") String numeroPiece,
            @RequestParam("type") TypePiece type,
            @RequestParam("categorieComptable") String categorieComptable,
            @RequestParam("datePiece") String datePiece,
            @RequestParam("montant") Double montant,
            @RequestParam("fournisseur") String fournisseur) {
        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setNumeroPiece(numeroPiece);
        request.setType(type);
        request.setCategorieComptable(categorieComptable);
        request.setDatePiece(datePiece);
        request.setMontant(montant);
        request.setFournisseur(fournisseur);
        return ResponseEntity.ok(documentService.uploadDocument(request, file));
    }

    @GetMapping("/my-documents")
    public ResponseEntity<List<DocumentResponse>> getMyDocuments() {
        return ResponseEntity.ok(documentService.getMyDocuments());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<DocumentResponse>> getPendingDocuments() {
        return ResponseEntity.ok(documentService.getPendingDocuments());
    }

    @GetMapping("/societe/{societeId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsBySociete(@PathVariable Long societeId) {
        return ResponseEntity.ok(documentService.getDocumentsBySociete(societeId));
    }

    @PutMapping("/{documentId}/validate")
    public ResponseEntity<DocumentResponse> validateDocument(
            @PathVariable Long documentId,
            @RequestBody DocumentValidationRequest request) {
        return ResponseEntity.ok(documentService.validateDocument(documentId, request.getCommentaire()));
    }

    @PutMapping("/{documentId}/reject")
    public ResponseEntity<DocumentResponse> rejectDocument(
            @PathVariable Long documentId,
            @RequestBody DocumentRejectionRequest request) {
        return ResponseEntity.ok(documentService.rejectDocument(documentId, request.getMotif()));
    }

    @GetMapping("/{documentId}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long documentId) {
        byte[] file = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document.pdf\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
