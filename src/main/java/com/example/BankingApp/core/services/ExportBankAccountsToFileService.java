package com.example.BankingApp.core.services;

import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ExportBankAccountsToFileService {

    private static final Logger logger = LogManager.getLogger(ExportBankAccountsToFileService.class);
    private final GetAllBankAccountsService getAllBankAccountsService;

    @Autowired
    public ExportBankAccountsToFileService(GetAllBankAccountsService getAllBankAccountsService) {
        this.getAllBankAccountsService = getAllBankAccountsService;
    }

    public void exportToCSV(String filePath) throws IOException {
        List<BankAccount> bankAccounts = getAllBankAccountsService.getAllBankAccounts();

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Write header
            fileWriter.append("ID,Holder,Balance\n");

            // Write data
            for (BankAccount account : bankAccounts) {
                fileWriter.append(String.valueOf(account.getId()))
                        .append(",")
                        .append(account.getHolder())
                        .append(",")
                        .append(String.valueOf(account.getBalance()))
                        .append("\n");
            }
        } catch (IOException e) {
            logger.error("Error occurred while exporting bank accounts to CSV: " + e.getMessage());
            throw e;
        }
        logger.info("Exported bank accounts to CSV successfully.");
    }
}