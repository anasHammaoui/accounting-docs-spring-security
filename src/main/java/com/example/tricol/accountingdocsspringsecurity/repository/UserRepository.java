package com.example.tricol.accountingdocsspringsecurity.repository;

import com.example.tricol.accountingdocsspringsecurity.model.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}