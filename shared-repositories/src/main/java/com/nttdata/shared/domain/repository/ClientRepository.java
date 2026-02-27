package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Client
 * Patrón: Repository
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Optional<Client> findByClientId(String clientId);
    
    Optional<Client> findByIdentification(String identificacion);
    
    boolean existsByClientId(String clientId);
    
    boolean existsByIdentification(String identificacion);
    
    @Query("SELECT c FROM Client c WHERE c.clientId = :clientId AND c.status.code = 'ACTIVO'")
    Optional<Client> findActiveByClientId(String clientId);
}
