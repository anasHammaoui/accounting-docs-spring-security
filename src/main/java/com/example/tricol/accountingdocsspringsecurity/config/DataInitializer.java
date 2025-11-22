package com.example.tricol.accountingdocsspringsecurity.config;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.enums.UserStatus;
import com.example.tricol.accountingdocsspringsecurity.model.Societe;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.repository.SocieteRepository;
import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SocieteRepository societeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            Societe societe1 = new Societe();
            societe1.setRaisonSociale("Tech Solutions SARL");
            societe1.setIce("001234567890001");
            societe1.setAdresse("123 Rue Mohammed V, Casablanca");
            societe1.setTelephone("+212 522 123456");
            societe1.setEmailContact("contact@techsolutions.ma");
            societe1 = societeRepository.save(societe1);

            User comptable = new User();
            comptable.setFullName("Ahmed Comptable");
            comptable.setEmail("comptable@example.com");
            comptable.setPassword(passwordEncoder.encode("password"));
            comptable.setRole(Role.COMPTABLE);
            comptable.setStatus(UserStatus.ACTIVE);
            userRepository.save(comptable);

            User societeUser = new User();
            societeUser.setFullName("Karim Manager");
            societeUser.setEmail("societe@example.com");
            societeUser.setPassword(passwordEncoder.encode("password"));
            societeUser.setRole(Role.SOCIETE);
            societeUser.setStatus(UserStatus.ACTIVE);
            societeUser.setSociete(societe1);
            userRepository.save(societeUser);
        }
    }
}
