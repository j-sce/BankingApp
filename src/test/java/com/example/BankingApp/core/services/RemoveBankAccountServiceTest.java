package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveBankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private RemoveBankAccountService removeBankAccountService;

    @Test
    void removeBankAccount_Success() {
        // Arrange
        Long accountId = 123L;
        when(bankAccountRepository.existsById(accountId)).thenReturn(true);

        // Act
        removeBankAccountService.removeBankAccount(accountId);

        // Assert
        verify(bankAccountRepository, times(1)).existsById(accountId);
        verify(bankAccountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void removeBankAccount_NotFound() {
        // Arrange
        Long accountId = 123L;
        when(bankAccountRepository.existsById(accountId)).thenReturn(false);

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> removeBankAccountService.removeBankAccount(accountId));

        // Assert
        verify(bankAccountRepository, times(1)).existsById(accountId);
        verify(bankAccountRepository, never()).deleteById(accountId);
        assert exception.getMessage().equals("Bank account not found for ID: " + accountId);
    }
}