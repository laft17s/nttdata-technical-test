package com.nttdata.client.application.service;

import com.nttdata.client.application.dto.ClientResponseDTO;
import com.nttdata.client.application.mapper.ClientMapper;
import com.nttdata.shared.domain.model.Client;
import com.nttdata.shared.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void getAllClients_ShouldReturnFluxOfClients() {
        // Arrange
        Client client1 = new Client();
        client1.setClientId("1");
        Client client2 = new Client();
        client2.setClientId("2");

        ClientResponseDTO dto1 = new ClientResponseDTO();
        dto1.setClientId("1");
        ClientResponseDTO dto2 = new ClientResponseDTO();
        dto2.setClientId("2");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));
        when(clientMapper.toResponseDTO(client1)).thenReturn(dto1);
        when(clientMapper.toResponseDTO(client2)).thenReturn(dto2);

        // Act & Assert
        StepVerifier.create(clientService.getAllClients())
            .expectNext(dto1)
            .expectNext(dto2)
            .verifyComplete();
    }
}
