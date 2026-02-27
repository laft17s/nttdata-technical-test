package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de catálogo para Tipo de Account
 * Patrón: Constants (tabla de catálogo)
 */
@Entity
@Table(name = "account_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String code; // AHORRO, CORRIENTE
    
    @Column(name = "descripcion", nullable = false, length = 50)
    private String description; // Account de Ahorro, Account Corriente
    
    @Column(name = "activo", nullable = false)
    private Boolean active = true;
}
