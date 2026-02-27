package com.nttdata.composite.model;

import java.math.BigDecimal;
import java.util.List;

public class Models {
    
    public record Person(
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone,
        Client client
    ) {}

    public record Client(
        String clientId,
        String status,
        List<Account> accounts
    ) {}

    public record Account(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        String status,
        List<Transaction> transactions
    ) {}

    public record Transaction(
        Long id,
        String date,
        String transactionType,
        BigDecimal amount,
        BigDecimal balance
    ) {}
}
