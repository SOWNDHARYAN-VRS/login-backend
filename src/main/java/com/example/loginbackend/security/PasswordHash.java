package com.example.loginbackend.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHash {
    final private BCryptPasswordEncoder passwordEncoder;
    public PasswordHash() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
