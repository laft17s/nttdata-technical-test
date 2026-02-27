package com.nttdata.composite.controller;

import com.nttdata.composite.client.AccountServiceClient;
import com.nttdata.composite.client.ClientServiceClient;
import com.nttdata.composite.dto.Dtos.AccountDTO;
import com.nttdata.composite.dto.Dtos.ClientDTO;
import com.nttdata.composite.dto.Dtos.TransactionDTO;
import com.nttdata.composite.dto.Dtos.ClientInputDTO;
import com.nttdata.composite.dto.Dtos.AccountInputDTO;
import com.nttdata.composite.dto.Dtos.CreateAccountDTO;
import com.nttdata.composite.dto.Dtos.CreateTransactionDTO;
import com.nttdata.composite.model.Models.Account;
import com.nttdata.composite.model.Models.Client;
import com.nttdata.composite.model.Models.Person;
import com.nttdata.composite.model.Models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final ClientServiceClient clientService;
    private final AccountServiceClient accountService;

    @QueryMapping
    public Mono<Person> person(@Argument String identification) {
        return clientService.getClientByIdentification(identification)
            .map(this::mapToPerson);
    }

    @QueryMapping
    public Flux<Person> people() {
        return clientService.getAllClients()
            .map(this::mapToPerson);
    }

    @SchemaMapping
    public Flux<Account> accounts(Client client) {
        return accountService.getAccountsByClient(client.clientId())
            .map(this::mapToAccount);
    }

    @SchemaMapping
    public Flux<Transaction> transactions(Account account) {
        return accountService.getTransactionsByAccount(account.accountNumber())
            .map(this::mapToTransaction);
    }

    @org.springframework.graphql.data.method.annotation.MutationMapping
    public Mono<Person> createClientWithAccount(
            @Argument ClientInputDTO client,
            @Argument AccountInputDTO account) {

        return clientService.createClient(client)
            .flatMap(createdClient -> {
                var createAccountDTO = new CreateAccountDTO(
                    account.accountNumber(),
                    account.accountType(),
                    account.initialBalance(),
                    account.status(),
                    createdClient.clientId()
                );

                return accountService.createAccount(createAccountDTO)
                    .flatMap(createdAccount -> {
                        var createTransactionDTO = new CreateTransactionDTO(
                            createdAccount.accountNumber(),
                            "DEPOSITO",
                            java.math.BigDecimal.valueOf(5)
                        );

                        return accountService.createTransaction(createTransactionDTO)
                            .thenReturn(createdClient);
                    });
            })
            .map(this::mapToPerson);
    }

    private Person mapToPerson(ClientDTO dto) {
        return new Person(
            dto.name(),
            dto.gender(),
            dto.age(),
            dto.identification(),
            dto.address(),
            dto.phone(),
            new Client(dto.clientId(), dto.status(), null)
        );
    }

    private Account mapToAccount(AccountDTO dto) {
        return new Account(
            dto.accountNumber(),
            dto.accountType(),
            dto.initialBalance(),
            dto.status(),
            null
        );
    }

    private Transaction mapToTransaction(TransactionDTO dto) {
        return new Transaction(
            dto.id(),
            dto.date().toString(),
            dto.transactionType(),
            dto.amount(),
            dto.balance()
        );
    }
}
