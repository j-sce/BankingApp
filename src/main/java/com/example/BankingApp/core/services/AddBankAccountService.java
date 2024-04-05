package com.example.BankingApp.core.services;


import com.example.BankingApp.core.database.BankAccountRepository;
import com.example.BankingApp.core.domain.BankAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddBankAccountService {

    private static final Logger logger = LogManager.getLogger(AddBankAccountService.class);
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public AddBankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public void addBankAccount(BankAccount bankAccount) {
        String holderName = bankAccount.getHolder();
        if (bankAccountRepository.findByHolder(holderName).isPresent()) {
            String errorMessage = "Holder " + holderName + " already has an existing bank account.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        bankAccountRepository.save(bankAccount);
        logger.info("Bank account added successfully for holder: {}", holderName);
    }
}