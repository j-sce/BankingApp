package com.example.BankingApp.core.services;

import com.example.BankingApp.core.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportBankAccountsToFileServiceTest {

    @Mock
    private GetAllBankAccountsService getAllBankAccountsService;

    @InjectMocks
    private ExportBankAccountsToFileService exportBankAccountsToFileService;

    @Test
    void exportToCSV_SuccessfulExport() throws IOException {
        // Arrange
        String filePath = "test.csv";
        List<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(new BankAccount(1L, "John Doe", 1000.0));
        bankAccounts.add(new BankAccount(2L, "Jane Smith", 2000.0));
        when(getAllBankAccountsService.getAllBankAccounts()).thenReturn(bankAccounts);

        // Act
        exportBankAccountsToFileService.exportToCSV(filePath);

        // Assert
        verify(getAllBankAccountsService, times(1)).getAllBankAccounts();
    }
}