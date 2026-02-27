package com.nttdata.client.application.strategy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementación de encriptación usando BCrypt
 * Patrón: Strategy (implementación concreta)
 */
@Component
public class BCryptPasswordEncryptionStrategy implements PasswordEncryptionStrategy {
    
    private final BCryptPasswordEncoder encoder;
    
    public BCryptPasswordEncryptionStrategy() {
        this.encoder = new BCryptPasswordEncoder();
    }
    
    @Override
    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    @Override
    public boolean matches(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
