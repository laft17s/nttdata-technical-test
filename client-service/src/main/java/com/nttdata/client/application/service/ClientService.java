package com.nttdata.client.application.service;

import com.nttdata.client.application.dto.ClientResponseDTO;
import com.nttdata.client.application.dto.CreateClientDTO;
import com.nttdata.client.application.dto.UpdateClientDTO;
import com.nttdata.client.application.factory.ClientFactory;
import com.nttdata.client.application.mapper.ClientMapper;
import com.nttdata.client.application.strategy.PasswordEncryptionStrategy;
import com.nttdata.client.infrastructure.kafka.ClientEventProducer;
import com.nttdata.common.constants.ErrorConstants;
import com.nttdata.common.exception.BusinessValidationException;
import com.nttdata.common.exception.ResourceNotFoundException;
import com.nttdata.shared.domain.model.Client;
import com.nttdata.shared.domain.model.Gender;
import com.nttdata.shared.domain.model.Status;
import com.nttdata.shared.domain.repository.ClientRepository;
import com.nttdata.shared.domain.repository.GenderRepository;
import com.nttdata.shared.domain.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * Servicio para gestión de clients
 * Patrón: Repository (acceso a datos)
 * Adaptado a Spring WebFlux (Reactivo + JPA con Scheduler)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    
    private final ClientRepository clientRepository;
    private final GenderRepository genderRepository;
    private final StatusRepository statusRepository;
    private final ClientFactory clientFactory;
    private final ClientMapper clientMapper;
    private final PasswordEncryptionStrategy passwordEncryptionStrategy;
    private final ClientEventProducer eventProducer;
    
    /**
     * Obtiene todos los clients
     */
    @Transactional(readOnly = true)
    public Flux<ClientResponseDTO> getAllClients() {
        log.debug("Obteniendo todos los clients");
        return Mono.fromCallable(() -> clientRepository.findAll().stream()
                .map(clientMapper::toResponseDTO)
                .toList())
            .subscribeOn(Schedulers.boundedElastic())
            .flatMapMany(Flux::fromIterable);
    }
    
    /**
     * Obtiene un cliente por su ID
     */
    @Transactional(readOnly = true)
    public Mono<ClientResponseDTO> getClientById(String clientId) {
        log.debug("Obteniendo cliente con ID: {}", clientId);
        return Mono.fromCallable(() -> {
                Client client = clientRepository.findByClientId(clientId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CLIENTE_NO_ENCONTRADO));
                return clientMapper.toResponseDTO(client);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Crea un nuevo cliente
     */
    @Transactional
    public Mono<ClientResponseDTO> createClient(CreateClientDTO dto) {
        log.debug("Creando nuevo cliente con identificación: {}", dto.getIdentification());
        
        return Mono.fromCallable(() -> {
            // Validar que no exista un cliente con la misma identificación
            if (clientRepository.existsByIdentification(dto.getIdentification())) {
                throw new BusinessValidationException(ErrorConstants.CLIENTE_YA_EXISTE);
            }
            
            // Crear cliente usando Factory pattern
            Client client = clientFactory.createFromDTO(dto);
            
            // Guardar cliente
            Client savedClient = clientRepository.save(client);
            log.info("Client creado exitosamente con ID: {}", savedClient.getClientId());
            
            // Publicar evento en Kafka (asíncrono)
            eventProducer.publishClientCreated(savedClient);
            
            return clientMapper.toResponseDTO(savedClient);
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Actualiza un cliente existente
     */
    @Transactional
    public Mono<ClientResponseDTO> updateClient(String clientId, UpdateClientDTO dto) {
        log.debug("Actualizando cliente con ID: {}", clientId);
        
        return Mono.fromCallable(() -> {
            // Buscar cliente existente
            Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CLIENTE_NO_ENCONTRADO));
            
            // Preparar datos para actualización
            Gender gender = null;
            if (dto.getGender() != null) {
                gender = genderRepository.findByCodeAndActiveTrue(dto.getGender())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorConstants.GENERO_NO_ENCONTRADO + ": " + dto.getGender()
                    ));
            }
            
            Status status = null;
            if (dto.getStatus() != null) {
                status = statusRepository.findByCodeAndActiveTrue(dto.getStatus())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorConstants.ESTADO_NO_ENCONTRADO + ": " + dto.getStatus()
                    ));
            }
            
            String encryptedPassword = null;
            if (dto.getPassword() != null) {
                encryptedPassword = passwordEncryptionStrategy.encrypt(dto.getPassword());
            }
            
            // Actualizar usando Factory (ahora retorna el cliente actualizado)
            Client updatedClientState = clientFactory.updateClient(
                client,
                dto.getName(),
                gender,
                dto.getAge(),
                dto.getAddress(),
                dto.getPhone(),
                status,
                encryptedPassword
            );
            
            // Guardar cambios
            Client updatedClient = clientRepository.save(updatedClientState);
            log.info("Client actualizado exitosamente con ID: {}", clientId);
            
            // Publicar evento en Kafka
            eventProducer.publishClientUpdated(updatedClient);
            
            return clientMapper.toResponseDTO(updatedClient);
        }).subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Elimina un cliente (soft delete - cambiar estado a INACTIVO)
     */
    @Transactional
    public Mono<Void> deleteClient(String clientId) {
        log.debug("Eliminando cliente con ID: {}", clientId);
        
        return Mono.fromRunnable(() -> {
            Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CLIENTE_NO_ENCONTRADO));
            
            // Soft delete: cambiar estado a INACTIVO
            Status statusInactivo = statusRepository.findByCode("INACTIVO")
                .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.ESTADO_NO_ENCONTRADO));
            
            Client updatedClient = client.toBuilder()
                .status(statusInactivo)
                .build();
            clientRepository.save(updatedClient);
            
            log.info("Client eliminado (inactivado) exitosamente con ID: {}", clientId);
            
            // Publicar evento en Kafka
            eventProducer.publishClientDeleted(client);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
    
    /**
     * Obtiene un cliente por su identificación
     */
    @Transactional(readOnly = true)
    public Mono<ClientResponseDTO> getClientByIdentificacion(String identificacion) {
        log.debug("Obteniendo cliente con identificación: {}", identificacion);
        return Mono.fromCallable(() -> {
                Client client = clientRepository.findByIdentification(identificacion)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorConstants.CLIENTE_NO_ENCONTRADO));
                return clientMapper.toResponseDTO(client);
            })
            .subscribeOn(Schedulers.boundedElastic());
    }
}
