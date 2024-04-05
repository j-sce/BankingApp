package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    private static final Logger logger = LogManager.getLogger(DepositService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public DepositService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public void depositToAccount(Long accountId, Double depositAmount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Bank account not found for ID: " + accountId);
                    return new IllegalArgumentException("Bank account not found for ID: " + accountId);
                });

        bankAccount.deposit(depositAmount);
        bankAccountRepository.save(bankAccount);
        logger.info("Deposit successful. New balance: " + bankAccount.getBalance());
    }
}