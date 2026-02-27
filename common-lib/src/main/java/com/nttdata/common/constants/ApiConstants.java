package com.nttdata.common.constants;

/**
 * Constantes para rutas de API
 * Patrón: Constants
 */
public final class ApiConstants {
    
    private ApiConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes");
    }
    
    // Base paths
    public static final String API_BASE_PATH = "/api";
    public static final String API_VERSION_1 = "/v1";
    
    // Client Service endpoints
    public static final String CLIENTES_PATH = "/clients";
    
    // Account Service endpoints
    public static final String CUENTAS_PATH = "/accounts";
    public static final String MOVIMIENTOS_PATH = "/transactions";
    public static final String REPORTES_PATH = "/reports";
    
    // Path parameters
    public static final String ID_PATH_PARAM = "/{id}";
    public static final String CLIENTE_ID_PATH_PARAM = "/{clientId}";
    public static final String NUMERO_CUENTA_PATH_PARAM = "/{accountNumber}";
    
    // Query parameters
    public static final String FECHA_PARAM = "fecha";
    public static final String CLIENTE_PARAM = "cliente";
    
    // Headers
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
}
