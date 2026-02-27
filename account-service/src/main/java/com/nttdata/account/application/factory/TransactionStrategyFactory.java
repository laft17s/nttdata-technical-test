package com.nttdata.account.application.factory;

import com.nttdata.account.application.strategy.DepositStrategy;
import com.nttdata.account.application.strategy.TransactionStrategy;
import com.nttdata.account.application.strategy.WithdrawalStrategy;
import com.nttdata.shared.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory para obtener la estrategia de transacción adecuada
 * Patrón: Factory + Strategy
 */
@Component
@RequiredArgsConstructor
public class TransactionStrategyFactory {
    
    private final DepositStrategy depositStrategy;
    private final WithdrawalStrategy withdrawalStrategy;
    
    /**
     * Obtiene la estrategia apropiada según el tipo de movimiento
     * @param tipoTransaction tipo de movimiento (DEPOSITO o RETIRO)
     * @return estrategia correspondiente
     */
    public TransactionStrategy getStrategy(Transaction.TransactionType tipoTransaction) {
        return switch (tipoTransaction) {
            case DEPOSITO -> depositStrategy;
            case RETIRO -> withdrawalStrategy;
        };
    }
    
    /**
     * Obtiene la estrategia apropiada según el tipo de movimiento en String
     * @param tipoTransaction tipo de movimiento como String
     * @return estrategia correspondiente
     */
    public TransactionStrategy getStrategy(String tipoTransaction) {
        Transaction.TransactionType tipo = Transaction.TransactionType.valueOf(tipoTransaction.toUpperCase());
        return getStrategy(tipo);
    }
}
