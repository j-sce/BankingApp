package com.example.BankingApp.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    void deposit_ValidDeposit_Success() {
        // Arrange
        BankAccount account = new BankAccount("John Doe", 1000.0);
        double depositAmount = 500.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    void deposit_NegativeDeposit_ExceptionThrown() {
        // Arrange
        BankAccount account = new BankAccount("John Doe", 1000.0);
        double negativeDepositAmount = -500.0;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.deposit(negativeDepositAmount));

        // Assert
        assertEquals("Amount must be positive.", exception.getMessage());
    }

    @Test
    void withdraw_ValidWithdraw_Success() {
        // Arrange
        BankAccount account = new BankAccount("John Doe", 1000.0);
        double withdrawAmount = 500.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void withdraw_InsufficientBalance_ExceptionThrown() {
        // Arrange
        BankAccount account = new BankAccount("John Doe", 500.0);
        double withdrawAmount = 1000.0;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(withdrawAmount));

        // Assert
        assertEquals("Withdrawal failed, insufficient balance!", exception.getMessage());
    }

    @Test
    void withdraw_NegativeWithdraw_ExceptionThrown() {
        // Arrange
        BankAccount account = new BankAccount("John Doe", 1000.0);
        double negativeWithdrawAmount = -500.0;

        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(negativeWithdrawAmount));

        // Assert
        assertEquals("Amount must be positive.", exception.getMessage());
    }
}