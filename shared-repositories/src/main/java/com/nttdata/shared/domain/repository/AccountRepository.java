package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Account
 * Patrón: Repository
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByClientId(String clientId);
    
    boolean existsByAccountNumber(String accountNumber);
    
    @Query("SELECT c FROM Account c WHERE c.clientId = :clientId AND c.status.code = 'ACTIVO'")
    List<Account> findActiveAccountsByClientId(String clientId);
}
