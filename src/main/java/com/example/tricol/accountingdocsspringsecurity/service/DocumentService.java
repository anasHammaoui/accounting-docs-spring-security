package com.example.tricol.accountingdocsspringsecurity.service;

import com.example.tricol.accountingdocsspringsecurity.dto.DocumentResponse;
import com.example.tricol.accountingdocsspringsecurity.dto.DocumentUploadRequest;
import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.model.Document;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.repository.DocumentRepository;
import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public DocumentResponse uploadDocument(DocumentUploadRequest request, MultipartFile file) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.SOCIETE || user.getSociete() == null) {
            throw new RuntimeException("Only societe users can upload documents");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new RuntimeException("File size exceeds 10MB");
        }

        String filename = fileStorageService.storeFile(file);

        Document document = new Document();
        document.setNumeroPiece(request.getNumeroPiece());
        document.setType(request.getType());
        document.setCategorieComptable(request.getCategorieComptable());
        document.setDatePiece(LocalDateTime.parse(request.getDatePiece(), DateTimeFormatter.ISO_DATE_TIME));
        document.setMontant(request.getMontant());
        document.setFournisseur(request.getFournisseur());
        document.setFichierPath(filename);
        document.setSociete(user.getSociete());
        document.setStatut(DocumentStatus.EN_ATTENTE);

        document = documentRepository.save(document);
        return mapToResponse(document);
    }

    public List<DocumentResponse> getMyDocuments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.SOCIETE || user.getSociete() == null) {
            throw new RuntimeException("Only societe users can view their documents");
        }

        return documentRepository.findBySocieteId(user.getSociete().getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getPendingDocuments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.COMPTABLE) {
            throw new RuntimeException("Only comptable can view pending documents");
        }

        return documentRepository.findByStatut(DocumentStatus.EN_ATTENTE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getDocumentsBySociete(Long societeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.COMPTABLE) {
            throw new RuntimeException("Only comptable can view documents by societe");
        }

        return documentRepository.findBySocieteId(societeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DocumentResponse validateDocument(Long documentId, String commentaire) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.COMPTABLE) {
            throw new RuntimeException("Only comptable can validate documents");
        }

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setStatut(DocumentStatus.VALIDE);
        document.setDateValidation(LocalDateTime.now());
        document.setCommentaire(commentaire);
        document.setDateModification(LocalDateTime.now());

        document = documentRepository.save(document);
        return mapToResponse(document);
    }

    public DocumentResponse rejectDocument(Long documentId, String motif) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.COMPTABLE) {
            throw new RuntimeException("Only comptable can reject documents");
        }

        if (motif == null || motif.trim().isEmpty()) {
            throw new RuntimeException("Motif is required for rejection");
        }

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setStatut(DocumentStatus.REJETE);
        document.setDateValidation(LocalDateTime.now());
        document.setCommentaire(motif);
        document.setDateModification(LocalDateTime.now());

        document = documentRepository.save(document);
        return mapToResponse(document);
    }

    public byte[] downloadDocument(Long documentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (user.getRole() == Role.SOCIETE && !document.getSociete().getId().equals(user.getSociete().getId())) {
            throw new RuntimeException("Access denied");
        }

        return fileStorageService.loadFile(document.getFichierPath());
    }

    private DocumentResponse mapToResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setNumeroPiece(document.getNumeroPiece());
        response.setType(document.getType());
        response.setCategorieComptable(document.getCategorieComptable());
        response.setDatePiece(document.getDatePiece());
        response.setMontant(document.getMontant());
        response.setFournisseur(document.getFournisseur());
        response.setStatut(document.getStatut());
        response.setDateValidation(document.getDateValidation());
        response.setCommentaire(document.getCommentaire());
        response.setSocieteNom(document.getSociete().getRaisonSociale());
        response.setSocieteId(document.getSociete().getId());
        response.setDateCreation(document.getDateCreation());
        return response;
    }
}
