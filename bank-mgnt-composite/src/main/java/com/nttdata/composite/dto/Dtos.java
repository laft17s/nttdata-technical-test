package com.nttdata.composite.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dtos {
    
    public record PersonDTO(
        Long id,
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone
    ) {}

    public record ClientDTO(
        String clientId,
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone,
        String status
    ) {}

    public record AccountDTO(
        Long id,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String status,
        String clientId
    ) {}

    public record TransactionDTO(
        Long id,
        LocalDateTime date,
        String transactionType,
        BigDecimal amount,
        BigDecimal balance,
        Long accountId
    ) {}

    public record ClientInputDTO(
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone,
        String password,
        String status
    ) {}

    public record AccountInputDTO(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String status
    ) {}

    public record CreateAccountDTO(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String status,
        String clientId
    ) {}

    public record CreateTransactionDTO(
        String accountNumber,
        String transactionType,
        BigDecimal amount
    ) {}
}
