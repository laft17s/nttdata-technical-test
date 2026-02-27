package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad AccountType
 * Patrón: Repository
 */
@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
    
    Optional<AccountType> findByCode(String codigo);
    
    Optional<AccountType> findByCodeAndActiveTrue(String codigo);
}
