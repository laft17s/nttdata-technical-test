package com.nttdata.account.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para crear un nuevo movimiento
 * Patrón: Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionDTO {
    
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;
    
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String transactionType; // DEPOSITO o RETIRO
    
    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor debe ser mayor a 0")
    private BigDecimal amount;
}
