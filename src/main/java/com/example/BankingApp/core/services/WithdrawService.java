package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WithdrawService {

    private static final Logger logger = LogManager.getLogger(WithdrawService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public WithdrawService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public boolean withdrawFromAccount(Long accountId, Double withdrawAmount) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> {
                    IllegalArgumentException exception = new IllegalArgumentException("Bank account not found for ID: " + accountId);
                    logger.error("Withdrawal failed: {}", exception.getMessage(), exception);
                    return exception;
                });

        try {
            bankAccount.withdraw(withdrawAmount);
            bankAccountRepository.save(bankAccount);
            logger.info("Withdrawal successful. New balance: {}", bankAccount.getBalance());
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Withdrawal failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}