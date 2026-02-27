package com.nttdata.client.infrastructure.controller;

import com.nttdata.client.application.dto.ClientResponseDTO;
import com.nttdata.client.application.dto.CreateClientDTO;
import com.nttdata.client.application.dto.UpdateClientDTO;
import com.nttdata.client.application.service.ClientService;
import com.nttdata.common.constants.ApiConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestión de clients
 * Endpoints: /api/clients
 * Adaptado a Spring WebFlux
 */
@RestController
@RequestMapping(ApiConstants.CLIENTES_PATH)
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    
    private final ClientService clienteService;
    
    /**
     * GET /api/clients
     * Obtiene todos los clients
     */
    @GetMapping
    public Flux<ClientResponseDTO> getAllClients() {
        log.info("GET /clients - Obteniendo todos los clients");
        return clienteService.getAllClients();
    }
    
    /**
     * GET /api/clients/{clientId}
     * Obtiene un cliente por ID
     */
    @GetMapping("/{clientId}")
    public Mono<ClientResponseDTO> getClientById(@PathVariable String clientId) {
        log.info("GET /clients/{} - Obteniendo cliente", clientId);
        return clienteService.getClientById(clientId);
    }
    
    /**
     * GET /api/clients/identificacion/{identificacion}
     * Obtiene un cliente por identificación
     */
    @GetMapping("/identificacion/{identificacion}")
    public Mono<ClientResponseDTO> getClientByIdentificacion(@PathVariable String identificacion) {
        log.info("GET /clients/identificacion/{} - Obteniendo cliente", identificacion);
        return clienteService.getClientByIdentificacion(identificacion);
    }
    
    /**
     * POST /api/clients
     * Crea un nuevo cliente
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientResponseDTO> createClient(@Valid @RequestBody CreateClientDTO dto) {
        log.info("POST /clients - Creando nuevo cliente");
        return clienteService.createClient(dto);
    }
    
    /**
     * PUT /api/clients/{clientId}
     * Actualiza un cliente existente
     */
    @PutMapping("/{clientId}")
    public Mono<ClientResponseDTO> updateClient(
            @PathVariable String clientId,
            @Valid @RequestBody UpdateClientDTO dto) {
        log.info("PUT /clients/{} - Actualizando cliente", clientId);
        return clienteService.updateClient(clientId, dto);
    }
    
    /**
     * PATCH /api/clients/{clientId}
     * Actualización parcial de un cliente
     */
    @PatchMapping("/{clientId}")
    public Mono<ClientResponseDTO> patchClient(
            @PathVariable String clientId,
            @RequestBody UpdateClientDTO dto) {
        log.info("PATCH /clients/{} - Actualización parcial de cliente", clientId);
        return clienteService.updateClient(clientId, dto);
    }
    
    /**
     * DELETE /api/clients/{clientId}
     * Elimina un cliente (soft delete)
     */
    @DeleteMapping("/{clientId}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable String clientId) {
        log.info("DELETE /clients/{} - Eliminando cliente", clientId);
        return clienteService.deleteClient(clientId)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
