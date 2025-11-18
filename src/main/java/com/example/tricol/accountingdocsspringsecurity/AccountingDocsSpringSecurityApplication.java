package com.example.tricol.accountingdocsspringsecurity;

import com.example.tricol.accountingdocsspringsecurity.enums.Role;
import com.example.tricol.accountingdocsspringsecurity.model.User;
import com.example.tricol.accountingdocsspringsecurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountingDocsSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingDocsSpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner initUsers(UserService userService){
        return args -> {
            if (userService.findByEmail("anas@gmail.com") == null){
                User user = new User("anas@gmail.com","anas123", Role.SOCIETE);
                userService.saveUser(user);
            }
            if (userService.findByEmail("moha@gmail.com") == null){
                User user = new User("moha@gmail.com","moha123", Role.COMPTABLE);
                userService.saveUser(user);
            }
        };
    }
}
