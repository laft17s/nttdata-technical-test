package com.nttdata.shared.domain.repository;

import com.nttdata.shared.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Transaction
 * Patrón: Repository
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t JOIN FETCH t.account")
    List<Transaction> findAllWithAccount();
    
    @Query("SELECT t FROM Transaction t JOIN FETCH t.account WHERE t.account.id = :cuentaId")
    List<Transaction> findByAccountId(@Param("cuentaId") Long cuentaId);
    
    @Query("SELECT t FROM Transaction t JOIN FETCH t.account WHERE t.account.id = :cuentaId ORDER BY t.date DESC")
    List<Transaction> findByAccountIdOrderByDateDesc(@Param("cuentaId") Long cuentaId);
    
    @Query("SELECT m FROM Transaction m WHERE m.account.id = :cuentaId AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<Transaction> findByAccountIdAndFechaBetween(
        @Param("cuentaId") Long cuentaId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT m FROM Transaction m WHERE m.account.clientId = :clientId AND m.date BETWEEN :startDate AND :endDate ORDER BY m.date DESC")
    List<Transaction> findByClientIdAndFechaBetween(
        @Param("clientId") String clientId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
}
