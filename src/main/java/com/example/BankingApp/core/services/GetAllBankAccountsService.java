package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAllBankAccountsService {

    private static final Logger logger = LogManager.getLogger(GetAllBankAccountsService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public GetAllBankAccountsService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        logger.info("Retrieved {} bank accounts", bankAccounts.size());
        return bankAccounts;
    }
}