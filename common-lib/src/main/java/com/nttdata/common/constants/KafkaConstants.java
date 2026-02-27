package com.nttdata.common.constants;

/**
 * Constantes para Kafka
 * Patrón: Constants
 */
public final class KafkaConstants {
    
    private KafkaConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes");
    }
    
    // Topics
    public static final String CLIENTE_EVENTS_TOPIC = "cliente-events";
    public static final String CUENTA_EVENTS_TOPIC = "cuenta-events";
    public static final String MOVIMIENTO_EVENTS_TOPIC = "movimiento-events";
    
    // Consumer Groups
    public static final String ACCOUNT_SERVICE_GROUP = "account-service-group";
    public static final String CLIENT_SERVICE_GROUP = "client-service-group";
    
    // Event Types
    public static final String CLIENTE_CREATED = "CLIENTE_CREATED";
    public static final String CLIENTE_UPDATED = "CLIENTE_UPDATED";
    public static final String CLIENTE_DELETED = "CLIENTE_DELETED";
    
    public static final String CUENTA_CREATED = "CUENTA_CREATED";
    public static final String CUENTA_UPDATED = "CUENTA_UPDATED";
    
    public static final String MOVIMIENTO_CREATED = "MOVIMIENTO_CREATED";
}
