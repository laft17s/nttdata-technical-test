package com.nttdata.client.application.mapper;

import com.nttdata.client.application.dto.ClientResponseDTO;
import com.nttdata.shared.domain.model.Client;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Client y DTOs
 */
@Component
public class ClientMapper {
    
    /**
     * Convierte una entidad Client a ClientResponseDTO
     * @param cliente entidad
     * @return DTO de respuesta
     */
    public ClientResponseDTO toResponseDTO(Client client) {
        return ClientResponseDTO.builder()
            .clientId(client.getClientId())
            .name(client.getName())
            .gender(client.getGender().getDescription())
            .age(client.getAge())
            .identification(client.getIdentification())
            .address(client.getAddress())
            .phone(client.getPhone())
            .status(client.getStatus().getDescription())
            .createdAt(client.getCreatedAt())
            .updatedAt(client.getUpdatedAt())
            .build();
    }
}
