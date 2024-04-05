package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private DepositService depositService;


    @Test
    void depositToAccount_Successful() {
        // Arrange
        Long accountId = 1L;
        Double depositAmount = 100.0;
        BankAccount bankAccount = new BankAccount("John Doe", 500.0);
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Act and Assert
        assertDoesNotThrow(() -> depositService.depositToAccount(accountId, depositAmount));

        // Assert
        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, times(1)).save(bankAccount);
        assertEquals(600.0, bankAccount.getBalance());
    }

    @Test
    void depositToAccount_AccountNotFound() {
        // Arrange
        Long accountId = 1L;
        Double depositAmount = 100.0;
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> depositService.depositToAccount(accountId, depositAmount));

        // Assert
        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, never()).save(any());
        assertEquals("Bank account not found for ID: " + accountId, exception.getMessage());
    }
}