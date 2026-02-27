package com.nttdata.account.application.service;

import com.nttdata.account.application.dto.AccountStatementReportDTO;
import com.nttdata.common.constants.ErrorConstants;
import com.nttdata.common.exception.BusinessValidationException;
import com.nttdata.common.exception.ResourceNotFoundException;
import com.nttdata.shared.domain.model.Account;
import com.nttdata.shared.domain.model.Transaction;
import com.nttdata.shared.domain.repository.AccountRepository;
import com.nttdata.shared.domain.repository.ClientRepository;
import com.nttdata.shared.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para generación de reports
 * Funcionalidad F4: Report de Estado de Account
 * Adaptado a Spring WebFlux (Reactivo + JPA con Scheduler)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;
    
    /**
     * Genera reporte de estado de cuenta por cliente y rango de fechas
     * @param clientId ID del cliente
     * @param startDate fecha de inicio del reporte
     * @param endDate fecha de fin del reporte
     * @return reporte de estado de cuenta
     */
    @Transactional(readOnly = true)
    public Mono<AccountStatementReportDTO> generarReport(String clientId, LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Generando reporte para cliente: {} desde {} hasta {}", clientId, startDate, endDate);
        
        return Mono.fromCallable(() -> {
            // Validar rango de fechas
            if (startDate.isAfter(endDate)) {
                throw new BusinessValidationException(ErrorConstants.RANGO_FECHAS_INVALIDO);
            }
            
            // Verificar que el cliente existe
            var cliente = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CLIENTE_NO_ENCONTRADO));
            
            // Obtener accounts del cliente
            List<Account> accounts = accountRepository.findByClientId(clientId);
            
            if (accounts.isEmpty()) {
                log.warn("Client {} no tiene accounts asociadas", clientId);
            }
            
            // Construir reporte
            List<AccountStatementReportDTO.AccountReportDTO> accountsReport = accounts.stream()
                .map(account -> buildAccountReport(account, startDate, endDate))
                .collect(Collectors.toList());
            
            return AccountStatementReportDTO.builder()
                .startDate(startDate)
                .endDate(endDate)
                .client(cliente.getName())
                .clientId(clientId)
                .accounts(accountsReport)
                .build();
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Construye el reporte de una cuenta específica
     */
    private AccountStatementReportDTO.AccountReportDTO buildAccountReport(
            Account account, LocalDateTime startDate, LocalDateTime endDate) {
        
        // Obtener transactions en el rango de fechas
        List<Transaction> transactions = transactionRepository.findByAccountIdAndFechaBetween(
            account.getId(), startDate, endDate
        );
        
        // Convertir transactions a DTO
        List<AccountStatementReportDTO.TransactionReportDTO> transactionsDTO = transactions.stream()
            .map(this::buildTransactionReport)
            .collect(Collectors.toList());
        
        return AccountStatementReportDTO.AccountReportDTO.builder()
            .accountNumber(account.getAccountNumber())
            .accountType(account.getAccountType().getDescription())
            .initialBalance(account.getInitialBalance())
            .currentBalance(account.getCurrentBalance())
            .status(account.getStatus().getDescription())
            .transactions(transactionsDTO)
            .build();
    }
    
    /**
     * Construye el DTO de un movimiento para el reporte
     */
    private AccountStatementReportDTO.TransactionReportDTO buildTransactionReport(Transaction transaction) {
        return AccountStatementReportDTO.TransactionReportDTO.builder()
            .date(transaction.getDate())
            .transactionType(transaction.getTransactionType().getDescription())
            .amount(transaction.getAmount())
            .balance(transaction.getBalance())
            .build();
    }
}
