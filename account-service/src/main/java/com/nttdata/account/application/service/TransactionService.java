package com.nttdata.account.application.service;

import com.nttdata.account.application.dto.CreateTransactionDTO;
import com.nttdata.account.application.dto.TransactionResponseDTO;
import com.nttdata.account.application.factory.TransactionStrategyFactory;
import com.nttdata.account.application.mapper.TransactionMapper;
import com.nttdata.account.application.strategy.TransactionStrategy;
import com.nttdata.common.constants.ErrorConstants;
import com.nttdata.common.exception.BusinessValidationException;
import com.nttdata.common.exception.ResourceNotFoundException;
import com.nttdata.shared.domain.model.Account;
import com.nttdata.shared.domain.model.Transaction;
import com.nttdata.shared.domain.repository.AccountRepository;
import com.nttdata.shared.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * Servicio para gestión de transactions
 * Implementa patrones: Strategy, Factory, Repository
 * Adaptado a Spring WebFlux (Reactivo + JPA con Scheduler)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionStrategyFactory strategyFactory;
    private final TransactionMapper transactionMapper;
    
    /**
     * Obtiene todos los transactions
     */
    @Transactional(readOnly = true)
    public Flux<TransactionResponseDTO> getAllTransactions() {
        log.debug("Obteniendo todos los transactions");
        return Mono.fromCallable(() -> transactionRepository.findAllWithAccount().stream()
                .map(transactionMapper::toResponseDTO)
                .toList())
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable);
    }
    
    /**
     * Obtiene un movimiento por ID
     */
    @Transactional(readOnly = true)
    public Mono<TransactionResponseDTO> getTransactionById(Long id) {
        log.debug("Obteniendo movimiento con ID: {}", id);
        return Mono.fromCallable(() -> {
                Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.MOVIMIENTO_NO_ENCONTRADO));
                return transactionMapper.toResponseDTO(transaction);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Crea un nuevo movimiento
     * Funcionalidades F2 y F3: Registro de transactions con validación de saldo
     */
    @Transactional
    public Mono<TransactionResponseDTO> createTransaction(CreateTransactionDTO dto) {
        log.debug("Creando nuevo movimiento para cuenta: {}", dto.getAccountNumber());
        
        return Mono.fromCallable(() -> {
            // Buscar cuenta
            Account account = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CUENTA_NO_ENCONTRADA));
            
            // Validar que la cuenta esté activa
            if (!"ACTIVO".equals(account.getStatus().getCode())) {
                throw new BusinessValidationException(ErrorConstants.CUENTA_INACTIVA);
            }
            
            // Validar valor del movimiento
            if (dto.getAmount() == null || dto.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new BusinessValidationException(ErrorConstants.VALOR_MOVIMIENTO_INVALIDO);
            }
            
            // Obtener estrategia apropiada usando Factory pattern
            TransactionStrategy strategy = strategyFactory.getStrategy(dto.getTransactionType());
            
            // Procesar movimiento usando Strategy pattern
            // Esto lanzará SaldoNoDisponibleException si no hay saldo (F3)
            Transaction transaction = strategy.procesarTransaction(account, dto.getAmount());
            
            // Guardar cuenta actualizada y movimiento
            accountRepository.save(transaction.getAccount());
            Transaction savedTransaction = transactionRepository.save(transaction);
            
            log.info("Transaction creado exitosamente. ID: {}, Tipo: {}, Valor: {}, Nuevo saldo: {}", 
                savedTransaction.getId(), 
                savedTransaction.getTransactionType(),
                savedTransaction.getAmount(),
                savedTransaction.getBalance());
            
            return transactionMapper.toResponseDTO(savedTransaction);
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Obtiene transactions por cuenta
     */
    @Transactional(readOnly = true)
    public Flux<TransactionResponseDTO> getTransactionsByAccount(String accountNumber) {
        log.debug("Obteniendo transactions para cuenta: {}", accountNumber);
        
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CUENTA_NO_ENCONTRADA));
            return transactionRepository.findByAccountIdOrderByDateDesc(account.getId())
                .stream()
                .map(transactionMapper::toResponseDTO)
                .toList();
        }).subscribeOn(Schedulers.boundedElastic())
          .flatMapMany(Flux::fromIterable);
    }
    
    /**
     * Elimina un movimiento
     */
    @Transactional
    public Mono<Void> deleteTransaction(Long id) {
        log.debug("Eliminando movimiento con ID: {}", id);
        
        return Mono.fromRunnable(() -> {
            Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.MOVIMIENTO_NO_ENCONTRADO));
            
            transactionRepository.delete(transaction);
            log.info("Transaction eliminado exitosamente con ID: {}", id);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
