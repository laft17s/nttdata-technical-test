package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de catálogo para Estado
 * Patrón: Constants (tabla de catálogo)
 */
@Entity
@Table(name = "status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String code; // ACTIVO, INACTIVO
    
    @Column(name = "descripcion", nullable = false, length = 50)
    private String description; // Activo, Inactivo
    
    @Column(name = "activo", nullable = false)
    private Boolean active = true;
}
