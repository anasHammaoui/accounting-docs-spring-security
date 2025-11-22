package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.enums.TypePiece;
import com.example.tricol.accountingdocsspringsecurity.model.Document;
import com.example.tricol.accountingdocsspringsecurity.model.Societe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SocieteRepository societeRepository;

    private Societe societe;

    @BeforeEach
    void setUp() {
        societe = new Societe();
        societe.setRaisonSociale("Test Company");
        societe.setIce("001234567890001");
        societe.setAdresse("Test Address");
        societe.setTelephone("0612345678");
        societe.setEmailContact("test@company.com");
        societe = societeRepository.save(societe);
    }

    @Test
    void findBySocieteId_Success() {
        Document doc1 = createDocument("FAC-001", DocumentStatus.EN_ATTENTE);
        Document doc2 = createDocument("FAC-002", DocumentStatus.VALIDE);
        documentRepository.save(doc1);
        documentRepository.save(doc2);

        List<Document> documents = documentRepository.findBySocieteId(societe.getId());

        assertEquals(2, documents.size());
    }

    @Test
    void findByStatut_Success() {
        Document doc1 = createDocument("FAC-001", DocumentStatus.EN_ATTENTE);
        Document doc2 = createDocument("FAC-002", DocumentStatus.EN_ATTENTE);
        Document doc3 = createDocument("FAC-003", DocumentStatus.VALIDE);
        documentRepository.save(doc1);
        documentRepository.save(doc2);
        documentRepository.save(doc3);

        List<Document> pending = documentRepository.findByStatut(DocumentStatus.EN_ATTENTE);

        assertEquals(2, pending.size());
        assertTrue(pending.stream().allMatch(d -> d.getStatut() == DocumentStatus.EN_ATTENTE));
    }

    @Test
    void findByStatut_Empty() {
        List<Document> rejected = documentRepository.findByStatut(DocumentStatus.REJETE);
        assertTrue(rejected.isEmpty());
    }

    private Document createDocument(String numeroPiece, DocumentStatus statut) {
        Document document = new Document();
        document.setNumeroPiece(numeroPiece);
        document.setType(TypePiece.FACTURE_ACHAT);
        document.setCategorieComptable("Test");
        document.setDatePiece(LocalDateTime.now());
        document.setMontant(1000.0);
        document.setFournisseur("Test Fournisseur");
        document.setFichierPath("test.pdf");
        document.setStatut(statut);
        document.setSociete(societe);
        return document;
    }
}
