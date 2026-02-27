package com.nttdata.account.application.service;

import com.nttdata.account.application.dto.TransactionResponseDTO;
import com.nttdata.account.application.mapper.TransactionMapper;
import com.nttdata.shared.domain.model.Transaction;
import com.nttdata.shared.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getAllTransactions_ShouldReturnFluxOfTransactions() {
        // Arrange
        Transaction t1 = new Transaction();
        t1.setId(1L);
        Transaction t2 = new Transaction();
        t2.setId(2L);

        TransactionResponseDTO dto1 = new TransactionResponseDTO();
        dto1.setId(1L);
        TransactionResponseDTO dto2 = new TransactionResponseDTO();
        dto2.setId(2L);

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(t1, t2));
        when(transactionMapper.toResponseDTO(t1)).thenReturn(dto1);
        when(transactionMapper.toResponseDTO(t2)).thenReturn(dto2);

        // Act & Assert
        StepVerifier.create(transactionService.getAllTransactions())
            .expectNext(dto1)
            .expectNext(dto2)
            .verifyComplete();
    }
}
