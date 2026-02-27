package com.nttdata.client.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un cliente existente
 * Patrón: Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateClientDTO {
    
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String name;
    
    private String gender; // Código: M, F, O
    
    @Min(value = 0, message = "La edad debe ser mayor o igual a 0")
    @Max(value = 150, message = "La edad debe ser menor o igual a 150")
    private Integer age;
    
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String address;
    
    @Pattern(regexp = "^[0-9]{9,20}$", message = "El teléfono debe contener entre 9 y 20 dígitos")
    private String phone;
    
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;
    
    private String status; // Código: ACTIVO, INACTIVO
}
