package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Transaction
 * Patrón: Builder para construcción fluida de objetos
 */
@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Column(name = "transaction_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    
    @NotNull(message = "El valor es obligatorio")
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @NotNull(message = "El saldo es obligatorio")
    @Column(name = "saldo", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "La cuenta es obligatoria")
    private Account account;
    
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
    
    /**
     * Enum para tipos de movimiento
     * Patrón: Constants
     */
    public enum TransactionType {
        DEPOSITO("Depósito"),
        RETIRO("Retiro");
        
        private final String description;
        
        TransactionType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}
