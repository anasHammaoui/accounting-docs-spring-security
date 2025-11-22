package com.example.tricol.accountingdocsspringsecurity.config;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.enums.UserStatus;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User comptable = new User();
            comptable.setFullName("Comptable User");
            comptable.setEmail("comptable@example.com");
            comptable.setPassword(passwordEncoder.encode("password"));
            comptable.setRole(Role.COMPTABLE);
            comptable.setStatus(UserStatus.ACTIVE);
            userRepository.save(comptable);

            User societe = new User();
            societe.setFullName("Societe User");
            societe.setEmail("societe@example.com");
            societe.setPassword(passwordEncoder.encode("password"));
            societe.setRole(Role.SOCIETE);
            societe.setStatus(UserStatus.ACTIVE);
            userRepository.save(societe);
        }
    }
}
