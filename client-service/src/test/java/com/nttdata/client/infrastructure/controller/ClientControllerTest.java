package com.nttdata.client.infrastructure.controller;

import com.nttdata.client.application.dto.ClientResponseDTO;
import com.nttdata.client.application.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = ClientController.class, excludeAutoConfiguration = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
class ClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ClientService clienteService;

    @Test
    void getAllClients_ShouldReturnOk() {
        // Arrange
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setClientId("1");
        dto.setName("John Doe");

        when(clienteService.getAllClients()).thenReturn(Flux.just(dto));

        // Act & Assert
        webTestClient.get()
            .uri("/clients")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(ClientResponseDTO.class)
            .hasSize(1)
            .contains(dto);
    }
}
