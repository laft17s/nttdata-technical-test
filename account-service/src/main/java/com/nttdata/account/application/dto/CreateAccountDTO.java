package com.nttdata.account.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para crear una nueva cuenta
 * Patrón: Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountDTO {
    
    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "^[0-9]{6,20}$", message = "El número de cuenta debe contener entre 6 y 20 dígitos")
    private String accountNumber;
    
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String accountType; // Código: AHORRO, CORRIENTE
    
    @NotNull(message = "El saldo inicial es obligatorio")
    @DecimalMin(value = "0.0", message = "El saldo inicial debe ser mayor o igual a 0")
    private BigDecimal initialBalance;
    
    @NotBlank(message = "El estado es obligatorio")
    private String status; // Código: ACTIVO, INACTIVO
    
    @NotBlank(message = "El ID del cliente es obligatorio")
    private String clientId;
}
