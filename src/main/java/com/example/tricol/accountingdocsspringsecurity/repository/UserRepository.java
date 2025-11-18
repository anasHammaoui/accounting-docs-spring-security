package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.model.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
