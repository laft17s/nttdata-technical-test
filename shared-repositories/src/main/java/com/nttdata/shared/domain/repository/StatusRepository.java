package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Estado
 * Patrón: Repository
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    
    Optional<Status> findByCode(String codigo);
    
    Optional<Status> findByCodeAndActiveTrue(String codigo);
}
