package com.example.BankingApp.controllers.rest;

import com.example.BankingApp.core.domain.BankAccount;
import com.example.BankingApp.core.requests.TransferRequest;
import com.example.BankingApp.core.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountRestController {

    private final GetAllBankAccountsService getAllBankAccountsService;
    private final AddBankAccountService addBankAccountService;
    private final RemoveBankAccountService removeBankAccountService;
    private final DepositService depositService;
    private final WithdrawService withdrawService;
    private final TransferService transferService;
    private final ExportBankAccountsToFileService exportBankAccountsToFileService;

    @Autowired
    public BankAccountRestController(GetAllBankAccountsService getAllBankAccountsService,
                                     AddBankAccountService addBankAccountService,
                                     RemoveBankAccountService removeBankAccountService,
                                     DepositService depositService,
                                     WithdrawService withdrawService,
                                     TransferService transferService, ExportBankAccountsToFileService exportBankAccountsToFileService) {
        this.getAllBankAccountsService = getAllBankAccountsService;
        this.addBankAccountService = addBankAccountService;
        this.removeBankAccountService = removeBankAccountService;
        this.depositService = depositService;
        this.withdrawService = withdrawService;
        this.transferService = transferService;
        this.exportBankAccountsToFileService = exportBankAccountsToFileService;
    }

    @GetMapping("/allBankAccounts")
    public List<BankAccount> getAllBankAccounts() {
        return getAllBankAccountsService.getAllBankAccounts();
    }

    @PostMapping("/")
    public void addNewBankAccount(@RequestBody BankAccount bankAccount) {
        addBankAccountService.addBankAccount(bankAccount);
    }

    @DeleteMapping("/{id}")
    public void removeBankAccount (@PathVariable Long id) {
        removeBankAccountService.removeBankAccount(id);
    }

    @PostMapping("/{accountId}/deposit")
    public void depositToAccount(@PathVariable Long accountId, @RequestBody Double depositAmount) {
        depositService.depositToAccount(accountId, depositAmount);
    }

    @PostMapping("/{accountId}/withdraw")
    public void withdrawFromAccount(@PathVariable Long accountId, @RequestBody Double withdrawAmount) {
        withdrawService.withdrawFromAccount(accountId, withdrawAmount);
    }

    @PostMapping("/transfer")
    public void transferFunds(@RequestBody TransferRequest transferRequest) {
        transferService.transferFunds(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount());
    }

    @GetMapping("/exportToCSV")
    public ResponseEntity<String> exportBankAccountsToCSV() {
        String filePath = "accounts.csv"; // File path for accounts.csv

        try {
            exportBankAccountsToFileService.exportToCSV(filePath);
            return ResponseEntity.ok("Bank accounts exported to CSV successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to export bank accounts to CSV: " + e.getMessage());
        }
    }

}