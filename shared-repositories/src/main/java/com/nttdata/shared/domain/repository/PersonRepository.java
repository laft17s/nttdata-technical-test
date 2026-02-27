package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Persona
 * Patrón: Repository
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
    Optional<Person> findByIdentification(String identificacion);
    
    boolean existsByIdentification(String identificacion);
}
