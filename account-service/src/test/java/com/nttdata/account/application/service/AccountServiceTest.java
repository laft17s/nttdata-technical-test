package com.nttdata.account.application.service;

import com.nttdata.account.application.dto.AccountResponseDTO;
import com.nttdata.account.application.mapper.AccountMapper;
import com.nttdata.shared.domain.model.Account;
import com.nttdata.shared.domain.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void getAllAccounts_ShouldReturnFluxOfAccounts() {
        // Arrange
        Account account1 = new Account();
        account1.setAccountNumber("123");
        Account account2 = new Account();
        account2.setAccountNumber("456");

        AccountResponseDTO dto1 = new AccountResponseDTO();
        dto1.setAccountNumber("123");
        AccountResponseDTO dto2 = new AccountResponseDTO();
        dto2.setAccountNumber("456");

        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));
        when(accountMapper.toResponseDTO(account1)).thenReturn(dto1);
        when(accountMapper.toResponseDTO(account2)).thenReturn(dto2);

        // Act & Assert
        StepVerifier.create(accountService.getAllAccounts())
            .expectNext(dto1)
            .expectNext(dto2)
            .verifyComplete();
    }
}
