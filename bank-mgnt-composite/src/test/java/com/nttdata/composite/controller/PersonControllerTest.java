package com.nttdata.composite.controller;

import com.nttdata.composite.client.ClientServiceClient;
import com.nttdata.composite.client.AccountServiceClient;
import com.nttdata.composite.dto.Dtos.ClientDTO;
import com.nttdata.composite.model.Models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private ClientServiceClient clientService;

    @Mock
    private AccountServiceClient accountService;

    @InjectMocks
    private PersonController personController;

    @Test
    void person_ShouldReturnPerson_WhenIdentificationExists() {
        // Arrange
        String identification = "1234567890";
        ClientDTO clientDTO = new ClientDTO(
            "1", "Jose Lema", "M", 30, identification, "Address", "099", "ACTIVO"
        );

        when(clientService.getClientByIdentification(identification))
            .thenReturn(Mono.just(clientDTO));

        // Act
        Mono<Person> result = personController.person(identification);

        // Assert
        StepVerifier.create(result)
            .expectNextMatches(person -> 
                person.name().equals("Jose Lema") && 
                person.identification().equals(identification)
            )
            .verifyComplete();
    }

    @Test
    void people_ShouldReturnListOfPersons() {
        // Arrange
        ClientDTO client1 = new ClientDTO(
            "1", "Jose Lema", "M", 30, "123", "Address", "099", "ACTIVO"
        );
        ClientDTO client2 = new ClientDTO(
            "2", "Maria", "F", 25, "456", "Address", "098", "ACTIVO"
        );

        when(clientService.getAllClients())
            .thenReturn(Flux.just(client1, client2));

        // Act
        Flux<Person> result = personController.people();

        // Assert
        StepVerifier.create(result)
            .expectNextCount(2)
            .verifyComplete();
    }
}
