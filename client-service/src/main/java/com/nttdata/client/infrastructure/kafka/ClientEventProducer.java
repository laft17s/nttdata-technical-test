package com.nttdata.client.infrastructure.kafka;

import com.nttdata.common.constants.KafkaConstants;
import com.nttdata.common.dto.ClientEventDTO;
import com.nttdata.shared.domain.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Producer de eventos de Client para Kafka
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ClientEventProducer {
    
    private final KafkaTemplate<String, ClientEventDTO> kafkaTemplate;
    
    /**
     * Publica evento de cliente creado
     */
    public void publishClientCreated(Client client) {
        ClientEventDTO event = buildEvent(client, KafkaConstants.CLIENTE_CREATED);
        sendEvent(event);
    }
    
    /**
     * Publica evento de cliente actualizado
     */
    public void publishClientUpdated(Client client) {
        ClientEventDTO event = buildEvent(client, KafkaConstants.CLIENTE_UPDATED);
        sendEvent(event);
    }
    
    /**
     * Publica evento de cliente eliminado
     */
    public void publishClientDeleted(Client client) {
        ClientEventDTO event = buildEvent(client, KafkaConstants.CLIENTE_DELETED);
        sendEvent(event);
    }
    
    /**
     * Construye un evento de cliente
     */
    private ClientEventDTO buildEvent(Client client, String eventType) {
        return ClientEventDTO.builder()
            .eventType(eventType)
            .clientId(client.getClientId())
            .name(client.getName())
            .identification(client.getIdentification())
            .status(client.getStatus().getCode())
            .timestamp(LocalDateTime.now())
            .correlationId(UUID.randomUUID().toString())
            .build();
    }
    
    /**
     * Envía el evento a Kafka
     */
    private void sendEvent(ClientEventDTO event) {
        CompletableFuture<SendResult<String, ClientEventDTO>> future = 
            kafkaTemplate.send(KafkaConstants.CLIENTE_EVENTS_TOPIC, event.getClientId(), event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Evento publicado exitosamente: {} para cliente: {}", 
                    event.getEventType(), event.getClientId());
            } else {
                log.error("Error al publicar evento: {} para cliente: {}", 
                    event.getEventType(), event.getClientId(), ex);
            }
        });
    }
}
