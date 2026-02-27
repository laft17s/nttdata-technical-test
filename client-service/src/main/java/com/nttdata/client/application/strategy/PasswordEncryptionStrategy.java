package com.nttdata.client.application.strategy;

/**
 * Interfaz para estrategias de encriptación de contraseñas
 * Patrón: Strategy
 */
public interface PasswordEncryptionStrategy {
    
    /**
     * Encripta una contraseña en texto plano
     * @param rawPassword contraseña sin encriptar
     * @return contraseña encriptada
     */
    String encrypt(String rawPassword);
    
    /**
     * Verifica si una contraseña coincide con su versión encriptada
     * @param rawPassword contraseña sin encriptar
     * @param encryptedPassword contraseña encriptada
     * @return true si coinciden, false en caso contrario
     */
    boolean matches(String rawPassword, String encryptedPassword);
}
