package com.example.tricol.accountingdocsspringsecurity.service;

import com.example.tricol.accountingdocsspringsecurity.dto.DocumentResponse;
import com.example.tricol.accountingdocsspringsecurity.dto.DocumentUploadRequest;
import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import com.example.tricol.accountingdocsspringsecurity.model.Document;
import com.example.tricol.accountingdocsspringsecurity.model.Societe;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.repository.DocumentRepository;
import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private DocumentService documentService;

    private User societeUser;
    private User comptableUser;
    private Societe societe;
    private Document document;

    @BeforeEach
    void setUp() {
        societe = new Societe();
        societe.setId(1L);
        societe.setRaisonSociale("Test Company");
        societe.setIce("001234567890001");

        societeUser = new User();
        societeUser.setId(1L);
        societeUser.setEmail("societe@test.com");
        societeUser.setRole(Role.SOCIETE);
        societeUser.setSociete(societe);

        comptableUser = new User();
        comptableUser.setId(2L);
        comptableUser.setEmail("comptable@test.com");
        comptableUser.setRole(Role.COMPTABLE);

        document = new Document();
        document.setId(1L);
        document.setNumeroPiece("FAC-001");
        document.setType(TypePiece.FACTURE_ACHAT);
        document.setMontant(1000.0);
        document.setSociete(societe);
        document.setStatut(DocumentStatus.EN_ATTENTE);
        document.setFichierPath("test.pdf");
    }

    @Test
    void uploadDocument_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("societe@test.com");
        when(userRepository.findByEmail("societe@test.com")).thenReturn(Optional.of(societeUser));
        when(fileStorageService.storeFile(any())).thenReturn("stored-file.pdf");
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentUploadRequest request = new DocumentUploadRequest();
        request.setNumeroPiece("FAC-001");
        request.setType(TypePiece.FACTURE_ACHAT);
        request.setMontant(1000.0);
        request.setDatePiece(LocalDateTime.now().toString());

        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test".getBytes());

        DocumentResponse response = documentService.uploadDocument(request, file);

        assertNotNull(response);
        assertEquals("FAC-001", response.getNumeroPiece());
        verify(documentRepository).save(any(Document.class));
    }

    @Test
    void getPendingDocuments_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("comptable@test.com");
        when(userRepository.findByEmail("comptable@test.com")).thenReturn(Optional.of(comptableUser));
        when(documentRepository.findByStatut(DocumentStatus.EN_ATTENTE)).thenReturn(Arrays.asList(document));

        List<DocumentResponse> responses = documentService.getPendingDocuments();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(DocumentStatus.EN_ATTENTE, responses.get(0).getStatut());
    }

    @Test
    void validateDocument_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("comptable@test.com");
        when(userRepository.findByEmail("comptable@test.com")).thenReturn(Optional.of(comptableUser));
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentResponse response = documentService.validateDocument(1L, "Approved");

        assertNotNull(response);
        verify(documentRepository).save(any(Document.class));
    }

    @Test
    void rejectDocument_WithoutMotif_ThrowsException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("comptable@test.com");
        when(userRepository.findByEmail("comptable@test.com")).thenReturn(Optional.of(comptableUser));

        assertThrows(RuntimeException.class, () -> {
            documentService.rejectDocument(1L, null);
        });
    }
}
