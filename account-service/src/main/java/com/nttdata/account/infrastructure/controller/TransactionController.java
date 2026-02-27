package com.nttdata.account.infrastructure.controller;

import com.nttdata.account.application.dto.CreateTransactionDTO;
import com.nttdata.account.application.dto.TransactionResponseDTO;
import com.nttdata.account.application.service.TransactionService;
import com.nttdata.common.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestión de transactions
 * Endpoints: /api/transactions
 * Adaptado a Spring WebFlux
 */
@RestController
@RequestMapping(ApiConstants.MOVIMIENTOS_PATH)
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    
    private final TransactionService transactionService;
    
    /**
     * GET /api/transactions
     * Obtiene todos los transactions
     */
    @GetMapping
    public Flux<TransactionResponseDTO> getAllTransactions() {
        log.info("GET /transactions - Obteniendo todos los transactions");
        return transactionService.getAllTransactions();
    }
    
    /**
     * GET /api/transactions/{id}
     * Obtiene un movimiento por ID
     */
    @GetMapping("/{id}")
    public Mono<TransactionResponseDTO> getTransactionById(@PathVariable Long id) {
        log.info("GET /transactions/{} - Obteniendo movimiento", id);
        return transactionService.getTransactionById(id);
    }
    
    /**
     * GET /api/transactions/cuenta/{accountNumber}
     * Obtiene transactions por cuenta
     */
    @GetMapping("/cuenta/{accountNumber}")
    public Flux<TransactionResponseDTO> getTransactionsByAccount(@PathVariable String accountNumber) {
        log.info("GET /transactions/cuenta/{} - Obteniendo transactions de la cuenta", accountNumber);
        return transactionService.getTransactionsByAccount(accountNumber);
    }
    
    /**
     * POST /api/transactions
     * Crea un nuevo movimiento
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponseDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO dto) {
        log.info("POST /transactions - Creando nuevo movimiento");
        return transactionService.createTransaction(dto);
    }
    
    /**
     * DELETE /api/transactions/{id}
     * Elimina un movimiento
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable Long id) {
        log.info("DELETE /transactions/{} - Eliminando movimiento", id);
        return transactionService.deleteTransaction(id)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
