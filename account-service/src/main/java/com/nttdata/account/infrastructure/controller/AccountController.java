package com.nttdata.account.infrastructure.controller;

import com.nttdata.account.application.dto.AccountResponseDTO;
import com.nttdata.account.application.dto.CreateAccountDTO;
import com.nttdata.account.application.service.AccountService;
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
 * Controlador REST para gestión de accounts
 * Endpoints: /api/accounts
 * Adaptado a Spring WebFlux
 */
@RestController
@RequestMapping(ApiConstants.CUENTAS_PATH)
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    
    private final AccountService accountService;
    
    /**
     * GET /api/accounts
     * Obtiene todas las accounts
     */
    @GetMapping
    public Flux<AccountResponseDTO> getAllAccounts() {
        log.info("GET /accounts - Obteniendo todas las accounts");
        return accountService.getAllAccounts();
    }
    
    /**
     * GET /api/accounts/{accountNumber}
     * Obtiene una cuenta por número
     */
    @GetMapping("/{accountNumber}")
    public Mono<AccountResponseDTO> getAccountByNumero(@PathVariable String accountNumber) {
        log.info("GET /accounts/{} - Obteniendo cuenta", accountNumber);
        return accountService.getAccountByNumero(accountNumber);
    }
    
    /**
     * GET /api/accounts/cliente/{clientId}
     * Obtiene accounts por cliente
     */
    @GetMapping("/cliente/{clientId}")
    public Flux<AccountResponseDTO> getAccountsByClient(@PathVariable String clientId) {
        log.info("GET /accounts/cliente/{} - Obteniendo accounts del cliente", clientId);
        return accountService.getAccountsByClient(clientId);
    }
    
    /**
     * POST /api/accounts
     * Crea una nueva cuenta
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountDTO dto) {
        log.info("POST /accounts - Creando nueva cuenta");
        return accountService.createAccount(dto);
    }
    
    /**
     * PUT /api/accounts/{accountNumber}
     * Actualiza una cuenta
     */
    @PutMapping("/{accountNumber}")
    public Mono<AccountResponseDTO> updateAccount(
            @PathVariable String accountNumber,
            @Valid @RequestBody CreateAccountDTO dto) {
        log.info("PUT /accounts/{} - Actualizando cuenta", accountNumber);
        return accountService.updateAccount(accountNumber, dto);
    }
    
    /**
     * DELETE /api/accounts/{accountNumber}
     * Elimina una cuenta (soft delete)
     */
    @DeleteMapping("/{accountNumber}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable String accountNumber) {
        log.info("DELETE /accounts/{} - Eliminando cuenta", accountNumber);
        return accountService.deleteAccount(accountNumber)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
