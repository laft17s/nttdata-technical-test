package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de catálogo para Género
 * Patrón: Constants (tabla de catálogo)
 */
@Entity
@Table(name = "gender")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gender {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String code; // M, F, O
    
    @Column(name = "descripcion", nullable = false, length = 50)
    private String description; // Masculino, Femenino, Otro
    
    @Column(name = "activo", nullable = false)
    private Boolean active = true;
}
