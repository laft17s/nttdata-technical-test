package com.nttdata.common.constants;

/**
 * Constantes para validaciones
 * Patrón: Constants
 */
public final class ValidationConstants {
    
    private ValidationConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes");
    }
    
    // Longitudes
    public static final int NOMBRE_MAX_LENGTH = 100;
    public static final int IDENTIFICACION_MAX_LENGTH = 20;
    public static final int DIRECCION_MAX_LENGTH = 200;
    public static final int TELEFONO_MAX_LENGTH = 20;
    public static final int CONTRASENA_MIN_LENGTH = 4;
    public static final int NUMERO_CUENTA_MAX_LENGTH = 20;
    
    // Valores numéricos
    public static final int EDAD_MIN = 0;
    public static final int EDAD_MAX = 150;
    
    // Regex patterns
    public static final String TELEFONO_PATTERN = "^[0-9]{9,20}$";
    public static final String IDENTIFICACION_PATTERN = "^[A-Z0-9]{5,20}$";
    public static final String NUMERO_CUENTA_PATTERN = "^[0-9]{6,20}$";
    
    // Códigos de catálogo
    public static final String GENERO_MASCULINO = "M";
    public static final String GENERO_FEMENINO = "F";
    public static final String GENERO_OTRO = "O";
    
    public static final String TIPO_CUENTA_AHORRO = "AHORRO";
    public static final String TIPO_CUENTA_CORRIENTE = "CORRIENTE";
    
    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_INACTIVO = "INACTIVO";
}
