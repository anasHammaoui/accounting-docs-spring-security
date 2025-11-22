package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.model.Societe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SocieteRepositoryTest {

    @Autowired
    private SocieteRepository societeRepository;

    @Test
    void save_Success() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test SARL");
        societe.setIce("001234567890001");
        societe.setAdresse("123 Rue Test");
        societe.setTelephone("0612345678");
        societe.setEmailContact("contact@test.ma");

        Societe saved = societeRepository.save(societe);

        assertNotNull(saved.getId());
        assertEquals("Test SARL", saved.getRaisonSociale());
    }

    @Test
    void findById_Success() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test SARL");
        societe.setIce("001234567890001");
        societe.setAdresse("123 Rue Test");
        societe.setTelephone("0612345678");
        societe.setEmailContact("contact@test.ma");
        Societe saved = societeRepository.save(societe);

        Optional<Societe> found = societeRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Test SARL", found.get().getRaisonSociale());
    }

    @Test
    void findById_NotFound() {
        Optional<Societe> found = societeRepository.findById(999L);
        assertFalse(found.isPresent());
    }
}
