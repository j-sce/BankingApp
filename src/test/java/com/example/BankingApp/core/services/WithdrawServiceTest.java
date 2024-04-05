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
class WithdrawServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private WithdrawService withdrawService;

    @Test
    void withdrawFromAccount_SuccessfulWithdrawal() {
        // Arrange
        Long accountId = 123L;
        Double initialBalance = 1000.0;
        Double withdrawAmount = 500.0;
        BankAccount bankAccount = new BankAccount("John Doe", initialBalance);
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Act
        boolean result = withdrawService.withdrawFromAccount(accountId, withdrawAmount);

        // Assert
        assertTrue(result);
        assertEquals(initialBalance - withdrawAmount, bankAccount.getBalance());
        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void withdrawFromAccount_AccountNotFound() {
        // Arrange
        Long accountId = 123L;
        Double withdrawAmount = 500.0;
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> withdrawService.withdrawFromAccount(accountId, withdrawAmount));

        // Assert
        assertEquals("Bank account not found for ID: " + accountId, exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void withdrawFromAccount_InsufficientBalance() {
        // Arrange
        Long accountId = 123L;
        Double initialBalance = 100.0;
        Double withdrawAmount = 500.0;
        BankAccount bankAccount = new BankAccount("John Doe", initialBalance);
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> withdrawService.withdrawFromAccount(accountId, withdrawAmount));

        // Assert
        assertEquals("Withdrawal failed, insufficient balance!", exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}