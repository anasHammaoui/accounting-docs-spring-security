package com.example.tricol.accountingdocsspringsecurity.service;

import com.example.tricol.accountingdocsspringsecurity.model.User;

public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
}
