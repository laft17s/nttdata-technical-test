package com.nttdata.common.exception;

/**
 * Excepción personalizada para saldo no disponible
 */
public class SaldoNoDisponibleException extends RuntimeException {
    
    public SaldoNoDisponibleException(String message) {
        super(message);
    }
    
    public SaldoNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
