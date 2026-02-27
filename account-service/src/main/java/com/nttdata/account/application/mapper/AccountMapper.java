package com.nttdata.account.application.mapper;

import com.nttdata.account.application.dto.AccountResponseDTO;
import com.nttdata.shared.domain.model.Account;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Account y DTOs
 */
@Component
public class AccountMapper {
    
    public AccountResponseDTO toResponseDTO(Account account) {
        return AccountResponseDTO.builder()
            .id(account.getId())
            .accountNumber(account.getAccountNumber())
            .accountType(account.getAccountType().getDescription())
            .initialBalance(account.getInitialBalance())
            .currentBalance(account.getCurrentBalance())
            .status(account.getStatus().getDescription())
            .clientId(account.getClientId())
            .createdAt(account.getCreatedAt())
            .updatedAt(account.getUpdatedAt())
            .build();
    }
}
