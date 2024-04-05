package com.example.BankingApp.core.services;

import com.example.BankingApp.core.database.BankAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RemoveBankAccountService {

    private static final Logger logger = LogManager.getLogger(RemoveBankAccountService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public RemoveBankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public void removeBankAccount(Long id) {
        if (!bankAccountRepository.existsById(id)) {
            logger.error("Bank account not found for ID: {}", id);
            throw new IllegalArgumentException("Bank account not found for ID: " + id);
        }

        bankAccountRepository.deleteById(id);
        logger.info("Bank account removed successfully. ID: {}", id);
    }
}