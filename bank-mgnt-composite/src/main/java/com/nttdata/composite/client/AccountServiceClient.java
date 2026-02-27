package com.nttdata.composite.client;

import com.nttdata.composite.dto.Dtos.AccountDTO;
import com.nttdata.composite.dto.Dtos.TransactionDTO;
import com.nttdata.composite.dto.Dtos.CreateAccountDTO;
import com.nttdata.composite.dto.Dtos.CreateTransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AccountServiceClient {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${services.account-service.url}")
    private String accountServiceUrl;

    public Flux<AccountDTO> getAccountsByClient(String clientId) {
        return webClientBuilder.build()
            .get()
            .uri(accountServiceUrl + "/api/accounts/cliente/" + clientId)
            .retrieve()
            .bodyToFlux(AccountDTO.class);
    }

    public Flux<TransactionDTO> getTransactionsByAccount(String accountNumber) {
        return webClientBuilder.build()
            .get()
            .uri(accountServiceUrl + "/api/transactions/cuenta/" + accountNumber)
            .retrieve()
            .bodyToFlux(TransactionDTO.class);
    }

    public Mono<AccountDTO> createAccount(CreateAccountDTO accountInput) {
        return webClientBuilder.build()
            .post()
            .uri(accountServiceUrl + "/api/accounts")
            .bodyValue(accountInput)
            .retrieve()
            .bodyToMono(AccountDTO.class);
    }

    public Mono<TransactionDTO> createTransaction(CreateTransactionDTO transactionInput) {
        return webClientBuilder.build()
            .post()
            .uri(accountServiceUrl + "/api/transactions")
            .bodyValue(transactionInput)
            .retrieve()
            .bodyToMono(TransactionDTO.class);
    }
}
