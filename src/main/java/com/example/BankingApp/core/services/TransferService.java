package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private static final Logger logger = LogManager.getLogger(TransferService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public TransferService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public boolean transferFunds(Long fromAccountId, Long toAccountId, Double transferAmount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId)
                .orElseThrow(() -> {
                    logger.error("Bank account not found for ID: {}", fromAccountId);
                    return new IllegalArgumentException("Bank account not found for ID: " + fromAccountId);
                });

        BankAccount toAccount = bankAccountRepository.findById(toAccountId)
                .orElseThrow(() -> {
                    logger.error("Bank account not found for ID: {}", toAccountId);
                    return new IllegalArgumentException("Bank account not found for ID: " + toAccountId);
                });

        try {
            fromAccount.withdraw(transferAmount);
            toAccount.deposit(transferAmount);

            bankAccountRepository.save(fromAccount);
            bankAccountRepository.save(toAccount);

            logger.info("Transfer successful.");
            logger.info("From Account New Balance: {}", fromAccount.getBalance());
            logger.info("To Account New Balance: {}", toAccount.getBalance());
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Transfer failed: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}