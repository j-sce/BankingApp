package com.example.BankingApp.core.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "holder")
    private String holder;

    @Column(name = "balance")
    private Double balance;

    public BankAccount() {
    }

    public BankAccount(String holder, Double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public BankAccount(String holder) {
        this(holder, 0D);
    }

    public BankAccount(Long id, String holder, Double balance) {
        this.id = id;
        this.holder = holder;
        this.balance = balance;
    }

    public void deposit(Double depositAmount) {
        validateAmount(depositAmount);
        balance += depositAmount;
    }

    public void withdraw(Double withdrawAmount) {
        validateAmount(withdrawAmount);
        if (withdrawAmount > balance) {
            throw new IllegalArgumentException("Withdrawal failed, insufficient balance!");
        }
        balance -= withdrawAmount;
    }

    public void printBalance() {
        System.out.println("Current Balance: " + balance);
    }

    private void validateAmount(Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return id.equals(that.id) && holder.equals(that.holder) && balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + id + ", holder='" + holder + '\'' + ", balance=" + balance + '}';
    }
}