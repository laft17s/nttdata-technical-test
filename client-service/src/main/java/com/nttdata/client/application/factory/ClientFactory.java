package com.nttdata.client.application.factory;

import com.nttdata.client.application.dto.CreateClientDTO;
import com.nttdata.client.application.strategy.PasswordEncryptionStrategy;
import com.nttdata.common.constants.ErrorConstants;
import com.nttdata.common.exception.ResourceNotFoundException;
import com.nttdata.shared.domain.model.Client;
import com.nttdata.shared.domain.model.Status;
import com.nttdata.shared.domain.model.Gender;
import com.nttdata.shared.domain.repository.StatusRepository;
import com.nttdata.shared.domain.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory para crear instancias de Client
 * Patrón: Factory
 */
@Component
@RequiredArgsConstructor
public class ClientFactory {
    
    private final GenderRepository genderRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncryptionStrategy passwordEncryptionStrategy;
    
    /**
     * Crea una nueva instancia de Client desde un DTO
     * @param dto datos del cliente
     * @return nueva instancia de Client
     */
    public Client createFromDTO(CreateClientDTO dto) {
        // Buscar género
        Gender gender = genderRepository.findByCodeAndActiveTrue(dto.getGender())
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorConstants.GENERO_NO_ENCONTRADO + ": " + dto.getGender()
            ));
        
        // Buscar estado
        Status status = statusRepository.findByCodeAndActiveTrue(dto.getStatus())
            .orElseThrow(() -> new ResourceNotFoundException(
                ErrorConstants.ESTADO_NO_ENCONTRADO + ": " + dto.getStatus()
            ));
        
        // Encriptar contraseña usando Strategy
        String encryptedPassword = passwordEncryptionStrategy.encrypt(dto.getPassword());
        
        // Crear Client usando Builder pattern
        return Client.builder()
            .name(dto.getName())
            .gender(gender)
            .age(dto.getAge())
            .identification(dto.getIdentification())
            .address(dto.getAddress())
            .phone(dto.getPhone())
            .password(encryptedPassword)
            .status(status)
            .build();
    }
    
    /**
     * Actualiza un Client existente con nuevos datos usando Builder
     */
    public Client updateClient(Client client, String name, Gender gender, Integer age,
                              String address, String phone, Status status, 
                              String encryptedPassword) {
        Client.ClientBuilder<?, ?> builder = client.toBuilder();
        
        if (name != null) builder.name(name);
        if (gender != null) builder.gender(gender);
        if (age != null) builder.age(age);
        if (address != null) builder.address(address);
        if (phone != null) builder.phone(phone);
        if (status != null) builder.status(status);
        if (encryptedPassword != null) builder.password(encryptedPassword);
        
        return builder.build();
    }
}
