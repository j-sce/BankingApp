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
class TransferServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transferFunds_SuccessfulTransfer() {
        // Arrange
        Long fromAccountId = 123L;
        Long toAccountId = 456L;
        Double initialBalanceFrom = 1000.0;
        Double initialBalanceTo = 500.0;
        Double transferAmount = 200.0;
        BankAccount fromAccount = new BankAccount(fromAccountId, "John Doe", initialBalanceFrom);
        BankAccount toAccount = new BankAccount(toAccountId, "Jane Smith", initialBalanceTo);
        when(bankAccountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(bankAccountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));

        // Act
        boolean result = transferService.transferFunds(fromAccountId, toAccountId, transferAmount);

        // Assert
        assertTrue(result);
        assertEquals(initialBalanceFrom - transferAmount, fromAccount.getBalance());
        assertEquals(initialBalanceTo + transferAmount, toAccount.getBalance());
        verify(bankAccountRepository, times(1)).findById(fromAccountId);
        verify(bankAccountRepository, times(1)).findById(toAccountId);
        verify(bankAccountRepository, times(1)).save(fromAccount);
        verify(bankAccountRepository, times(1)).save(toAccount);
    }

    @Test
    void transferFunds_AccountNotFound() {
        // Arrange
        Long fromAccountId = 123L;
        Long toAccountId = 456L;
        Double transferAmount = 200.0;
        when(bankAccountRepository.findById(fromAccountId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferService.transferFunds(fromAccountId, toAccountId, transferAmount));

        // Assert
        assertEquals("Bank account not found for ID: " + fromAccountId, exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(fromAccountId);
        verify(bankAccountRepository, never()).findById(toAccountId);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void transferFunds_ToAccountNotFound() {
        // Arrange
        Long fromAccountId = 123L;
        Long toAccountId = 456L;
        Double transferAmount = 200.0;
        BankAccount fromAccount = new BankAccount(fromAccountId, "John Doe", 1000.0);
        when(bankAccountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(bankAccountRepository.findById(toAccountId)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferService.transferFunds(fromAccountId, toAccountId, transferAmount));

        // Assert
        assertEquals("Bank account not found for ID: " + toAccountId, exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(fromAccountId);
        verify(bankAccountRepository, times(1)).findById(toAccountId);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void transferFunds_InsufficientFunds() {
        // Arrange
        Long fromAccountId = 123L;
        Long toAccountId = 456L;
        Double transferAmount = 2000.0;
        BankAccount fromAccount = new BankAccount(fromAccountId, "John Doe", 1000.0);
        BankAccount toAccount = new BankAccount(toAccountId, "Jane Smith", 500.0);
        when(bankAccountRepository.findById(fromAccountId)).thenReturn(Optional.of(fromAccount));
        when(bankAccountRepository.findById(toAccountId)).thenReturn(Optional.of(toAccount));

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> transferService.transferFunds(fromAccountId, toAccountId, transferAmount));

        // Assert
        assertEquals("Withdrawal failed, insufficient balance!", exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(fromAccountId);
        verify(bankAccountRepository, times(1)).findById(toAccountId);
        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

}