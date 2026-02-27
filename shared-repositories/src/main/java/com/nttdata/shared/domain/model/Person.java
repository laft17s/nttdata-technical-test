package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad base Persona
 * Patrón: Builder para construcción fluida de objetos
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    @NotNull(message = "El género es obligatorio")
    private Gender gender;
    
    @Positive(message = "La edad debe ser positiva")
    @Column(name = "edad", nullable = false)
    private Integer age;
    
    @NotBlank(message = "La identificación es obligatoria")
    @Column(name = "identificacion", nullable = false, unique = true, length = 20)
    private String identification;
    
    @Column(name = "direccion", length = 200)
    private String address;
    
    @Column(name = "telefono", length = 20)
    private String phone;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
