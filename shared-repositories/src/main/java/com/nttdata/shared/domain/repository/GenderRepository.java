package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Genero
 * Patrón: Repository
 */
@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    
    Optional<Gender> findByCode(String codigo);
    
    Optional<Gender> findByCodeAndActiveTrue(String codigo);
}
