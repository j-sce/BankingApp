package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllBankAccountsServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private GetAllBankAccountsService getAllBankAccountsService;


    @Test
    void getAllBankAccounts_Success() {
        // Arrange
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(new BankAccount("John Doe", 1000.0));
        bankAccounts.add(new BankAccount("Jane Smith", 2000.0));
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);

        // Act
        List<BankAccount> result = getAllBankAccountsService.getAllBankAccounts();

        // Assert
        verify(bankAccountRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getHolder());
        assertEquals(1000.0, result.get(0).getBalance());
        assertEquals("Jane Smith", result.get(1).getHolder());
        assertEquals(2000.0, result.get(1).getBalance());
    }

    @Test
    void getAllBankAccounts_EmptyList() {
        // Arrange
        List<BankAccount> bankAccounts = new ArrayList<>();
        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);

        // Act
        List<BankAccount> result = getAllBankAccountsService.getAllBankAccounts();

        // Assert
        verify(bankAccountRepository, times(1)).findAll();
        assertEquals(0, result.size());
    }
}