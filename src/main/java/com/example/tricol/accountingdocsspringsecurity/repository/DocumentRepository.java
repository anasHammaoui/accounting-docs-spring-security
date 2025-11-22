package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository  extends JpaRepository<Document, Long> {
}