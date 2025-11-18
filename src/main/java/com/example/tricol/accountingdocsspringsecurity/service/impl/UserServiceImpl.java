    package com.example.tricol.accountingdocsspringsecurity.service.impl;

    import com.example.tricol.accountingdocsspringsecurity.model.User;
    import com.example.tricol.accountingdocsspringsecurity.repository.UserRepository;
    import com.example.tricol.accountingdocsspringsecurity.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    @Service
    public class UserServiceImpl implements UserService {
        @Autowired
        UserRepository userRepository;
        private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        @Override
        public User saveUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        public User findByEmail(String email){
            return userRepository.findByEmail(email);
        }
    }
