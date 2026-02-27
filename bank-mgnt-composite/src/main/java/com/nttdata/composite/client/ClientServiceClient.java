package com.nttdata.composite.client;

import com.nttdata.composite.dto.Dtos.ClientDTO;
import com.nttdata.composite.dto.Dtos.ClientInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClientServiceClient {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${services.client-service.url}")
    private String clientServiceUrl;

    public Mono<ClientDTO> getClientByIdentification(String identification) {
        return webClientBuilder.build()
            .get()
            .uri(clientServiceUrl + "/api/clients/identificacion/" + identification)
            .retrieve()
            .bodyToMono(ClientDTO.class);
    }

    public Mono<ClientDTO> createClient(ClientInputDTO clientInput) {
        return webClientBuilder.build()
            .post()
            .uri(clientServiceUrl + "/api/clients")
            .bodyValue(clientInput)
            .retrieve()
            .bodyToMono(ClientDTO.class);
    }
    public Flux<ClientDTO> getAllClients() {
        return webClientBuilder.build()
            .get()
            .uri(clientServiceUrl + "/api/clients")
            .retrieve()
            .bodyToFlux(ClientDTO.class);
    }
}
