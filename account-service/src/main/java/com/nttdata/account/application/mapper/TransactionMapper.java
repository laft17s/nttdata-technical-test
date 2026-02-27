package com.nttdata.account.application.mapper;

import com.nttdata.account.application.dto.TransactionResponseDTO;
import com.nttdata.shared.domain.model.Transaction;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Transaction y DTOs
 */
@Component
public class TransactionMapper {
    
    public TransactionResponseDTO toResponseDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
            .id(transaction.getId())
            .date(transaction.getDate())
            .transactionType(transaction.getTransactionType().name())
            .amount(transaction.getAmount())
            .balance(transaction.getBalance())
            .accountNumber(transaction.getAccount().getAccountNumber())
            .build();
    }
}
