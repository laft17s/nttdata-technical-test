package com.nttdata.account.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para reporte de estado de cuenta
 * Patrón: Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatementReportDTO {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;
    
    private String client;
    private String clientId;
    
    private List<AccountReportDTO> accounts;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AccountReportDTO {
        private String accountNumber;
        private String accountType;
        private BigDecimal initialBalance;
        private BigDecimal currentBalance;
        private String status;
        private List<TransactionReportDTO> transactions;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TransactionReportDTO {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime date;
        private String transactionType;
        private BigDecimal amount;
        private BigDecimal balance;
    }
}
