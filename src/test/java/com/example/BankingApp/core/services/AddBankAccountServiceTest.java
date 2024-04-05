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
class AddBankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private AddBankAccountService addBankAccountService;

    @Test
    void addBankAccount_Successful() {
        // Arrange
        BankAccount bankAccount = new BankAccount("John Doe", 1000.0);

        // Act
        when(bankAccountRepository.findByHolder("John Doe")).thenReturn(Optional.empty());

        // Assert
        assertDoesNotThrow(() -> addBankAccountService.addBankAccount(bankAccount));

        verify(bankAccountRepository, times(1)).findByHolder("John Doe");
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void addBankAccount_AlreadyExists() {
        // Arrange
        BankAccount existingAccount = new BankAccount("John Doe", 500.0);

        // Act
        when(bankAccountRepository.findByHolder("John Doe")).thenReturn(Optional.of(existingAccount));


        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                addBankAccountService.addBankAccount(new BankAccount("John Doe", 1000.0)));

        assertEquals("Holder John Doe already has an existing bank account.", exception.getMessage());

        verify(bankAccountRepository, times(1)).findByHolder("John Doe");
        verify(bankAccountRepository, never()).save(any());
    }
}