package com.nttdata.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para eventos de Client en Kafka
 * Patrón: Builder
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientEventDTO {
    
    private String eventType; // CLIENTE_CREATED, CLIENTE_UPDATED, CLIENTE_DELETED
    
    private String clientId;
    
    private String name;
    
    private String identification;
    
    private String status;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String correlationId;
}
