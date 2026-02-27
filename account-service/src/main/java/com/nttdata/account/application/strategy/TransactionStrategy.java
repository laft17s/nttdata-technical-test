package com.nttdata.account.application.strategy;

import com.nttdata.shared.domain.model.Account;
import com.nttdata.shared.domain.model.Transaction;

import java.math.BigDecimal;

/**
 * Interfaz para estrategias de procesamiento de transactions
 * Patrón: Strategy
 */
public interface TransactionStrategy {
    
    /**
     * Procesa un movimiento en una cuenta
     * @param cuenta cuenta donde se realizará el movimiento
     * @param valor valor del movimiento
     * @return movimiento creado
     * @throws com.nttdata.common.exception.SaldoNoDisponibleException si no hay saldo suficiente
     */
    Transaction procesarTransaction(Account account, BigDecimal amount);
    
    /**
     * Valida si el movimiento puede ser procesado
     * @param cuenta cuenta donde se realizará el movimiento
     * @param valor valor del movimiento
     * @return true si el movimiento es válido
     */
    boolean validarTransaction(Account account, BigDecimal amount);
}
