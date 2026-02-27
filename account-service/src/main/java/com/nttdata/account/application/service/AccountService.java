package com.nttdata.account.application.service;

import com.nttdata.account.application.dto.AccountResponseDTO;
import com.nttdata.account.application.dto.CreateAccountDTO;
import com.nttdata.account.application.mapper.AccountMapper;
import com.nttdata.common.constants.ErrorConstants;
import com.nttdata.common.exception.BusinessValidationException;
import com.nttdata.common.exception.ResourceNotFoundException;
import com.nttdata.shared.domain.model.Account;
import com.nttdata.shared.domain.model.AccountType;
import com.nttdata.shared.domain.model.Status;
import com.nttdata.shared.domain.repository.AccountRepository;
import com.nttdata.shared.domain.repository.AccountTypeRepository;
import com.nttdata.shared.domain.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * Servicio para gestión de accounts
 * Patrón: Repository
 * Adaptado a Spring WebFlux (Reactivo + JPA con Scheduler)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final StatusRepository statusRepository;
    private final AccountMapper accountMapper;
    
    /**
     * Obtiene todas las accounts
     */
    @Transactional(readOnly = true)
    public Flux<AccountResponseDTO> getAllAccounts() {
        log.debug("Obteniendo todas las accounts");
        return Mono.fromCallable(() -> accountRepository.findAll().stream()
                .map(accountMapper::toResponseDTO)
                .toList())
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable);
    }
    
    /**
     * Obtiene una cuenta por número
     */
    @Transactional(readOnly = true)
    public Mono<AccountResponseDTO> getAccountByNumero(String accountNumber) {
        log.debug("Obteniendo cuenta: {}", accountNumber);
        return Mono.fromCallable(() -> {
                Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CUENTA_NO_ENCONTRADA));
                return accountMapper.toResponseDTO(account);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Obtiene accounts por cliente
     */
    @Transactional(readOnly = true)
    public Flux<AccountResponseDTO> getAccountsByClient(String clientId) {
        log.debug("Obteniendo accounts del cliente: {}", clientId);
        return Mono.fromCallable(() -> accountRepository.findByClientId(clientId).stream()
                .map(accountMapper::toResponseDTO)
                .toList())
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable);
    }
    
    /**
     * Crea una nueva cuenta
     */
    @Transactional
    public Mono<AccountResponseDTO> createAccount(CreateAccountDTO dto) {
        log.debug("Creando nueva cuenta: {}", dto.getAccountNumber());
        
        return Mono.fromCallable(() -> {
            // Validar que no exista una cuenta con el mismo número
            if (accountRepository.existsByAccountNumber(dto.getAccountNumber())) {
                throw new BusinessValidationException(ErrorConstants.CUENTA_YA_EXISTE);
            }
            
            // Buscar tipo de cuenta
            AccountType accountType = accountTypeRepository.findByCodeAndActiveTrue(dto.getAccountType())
                .orElseThrow(() -> new ResourceNotFoundException(
                    ErrorConstants.TIPO_CUENTA_NO_ENCONTRADO + ": " + dto.getAccountType()
                ));
            
            // Buscar estado
            Status status = statusRepository.findByCodeAndActiveTrue(dto.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException(
                    ErrorConstants.ESTADO_NO_ENCONTRADO + ": " + dto.getStatus()
                ));
            
            // Crear cuenta usando Builder
            Account account = Account.builder()
                .accountNumber(dto.getAccountNumber())
                .accountType(accountType)
                .initialBalance(dto.getInitialBalance())
                .currentBalance(dto.getInitialBalance())
                .status(status)
                .clientId(dto.getClientId())
                .build();
            
            // Guardar cuenta
            Account savedAccount = accountRepository.save(account);
            log.info("Account creada exitosamente: {}", savedAccount.getAccountNumber());
            
            return accountMapper.toResponseDTO(savedAccount);
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Actualiza una cuenta
     */
    @Transactional
    public Mono<AccountResponseDTO> updateAccount(String accountNumber, CreateAccountDTO dto) {
        log.debug("Actualizando cuenta: {}", accountNumber);
        
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CUENTA_NO_ENCONTRADA));
            
            // Actualizar cuenta usando Builder si hay cambios
            Account.AccountBuilder builder = account.toBuilder();
            
            if (dto.getAccountType() != null) {
                AccountType accountType = accountTypeRepository.findByCodeAndActiveTrue(dto.getAccountType())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.TIPO_CUENTA_NO_ENCONTRADO));
                builder.accountType(accountType);
            }
            
            if (dto.getStatus() != null) {
                Status status = statusRepository.findByCodeAndActiveTrue(dto.getStatus())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.ESTADO_NO_ENCONTRADO));
                builder.status(status);
            }
            
            Account updatedAccount = accountRepository.save(builder.build());
            log.info("Account actualizada exitosamente: {}", accountNumber);
            
            return accountMapper.toResponseDTO(updatedAccount);
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Elimina una cuenta (soft delete)
     */
    @Transactional
    public Mono<Void> deleteAccount(String accountNumber) {
        log.debug("Eliminando cuenta: {}", accountNumber);
        
        return Mono.fromRunnable(() -> {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CUENTA_NO_ENCONTRADA));
            
            // Soft delete: cambiar estado a INACTIVO
            Status statusInactivo = statusRepository.findByCode("INACTIVO")
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.ESTADO_NO_ENCONTRADO));
            
            Account updatedAccount = account.toBuilder()
                .status(statusInactivo)
                .build();
            accountRepository.save(updatedAccount);
            
            log.info("Account eliminada (inactivada) exitosamente: {}", accountNumber);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
