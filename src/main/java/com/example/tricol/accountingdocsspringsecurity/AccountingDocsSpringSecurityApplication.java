package com.example.tricol.accountingdocsspringsecurity;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountingDocsSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingDocsSpringSecurityApplication.class, args);
    }
}
