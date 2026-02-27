package com.nttdata.shared.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entidad Client que hereda de Persona
 * Patrón: Builder para construcción fluida de objetos
 */
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "person_id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Client extends Person {
    
    @Column(name = "client_id", nullable = false, unique = true, updatable = false)
    private String clientId;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "contrasena", nullable = false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private Status status;
    
    @PrePersist
    protected void onClientCreate() {
        super.onCreate();
        if (clientId == null || clientId.isEmpty()) {
            clientId = UUID.randomUUID().toString();
        }
    }
}
