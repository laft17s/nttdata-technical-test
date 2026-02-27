package com.nttdata.common.constants;

/**
 * Constantes para mensajes de error
 * Patrón: Constants
 */
public final class ErrorConstants {
    
    private ErrorConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes");
    }
    
    // Errores generales
    public static final String ERROR_INTERNO = "Ha ocurrido un error interno en el servidor";
    public static final String ERROR_VALIDACION = "Error de validación en los datos proporcionados";
    public static final String ERROR_RECURSO_NO_ENCONTRADO = "Recurso no encontrado";
    
    // Errores de Client
    public static final String CLIENTE_NO_ENCONTRADO = "Client no encontrado";
    public static final String CLIENTE_YA_EXISTE = "Ya existe un cliente con la identificación proporcionada";
    public static final String CLIENTE_INACTIVO = "El cliente está inactivo";
    
    // Errores de Account
    public static final String CUENTA_NO_ENCONTRADA = "Account no encontrada";
    public static final String CUENTA_YA_EXISTE = "Ya existe una cuenta con el número proporcionado";
    public static final String CUENTA_INACTIVA = "La cuenta está inactiva";
    public static final String SALDO_NO_DISPONIBLE = "Saldo no disponible";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente para realizar la operación";
    
    // Errores de Transaction
    public static final String MOVIMIENTO_NO_ENCONTRADO = "Transaction no encontrado";
    public static final String VALOR_MOVIMIENTO_INVALIDO = "El valor del movimiento debe ser mayor a cero";
    
    // Errores de validación
    public static final String CAMPO_REQUERIDO = "El campo %s es requerido";
    public static final String FORMATO_INVALIDO = "El formato del campo %s es inválido";
    public static final String RANGO_FECHAS_INVALIDO = "El rango de fechas es inválido";
    
    // Errores de catálogos
    public static final String GENERO_NO_ENCONTRADO = "Género no encontrado";
    public static final String TIPO_CUENTA_NO_ENCONTRADO = "Tipo de cuenta no encontrado";
    public static final String ESTADO_NO_ENCONTRADO = "Estado no encontrado";
}
