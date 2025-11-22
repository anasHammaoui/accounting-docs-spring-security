package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.enums.DocumentStatus;
import com.example.tricol.accountingdocsspringsecurity.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findBySocieteId(Long societeId);
    List<Document> findByStatut(DocumentStatus statut);
}