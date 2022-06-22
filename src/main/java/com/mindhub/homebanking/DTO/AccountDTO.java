package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


public class AccountDTO {


    private final long id ;

    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private Set<TransactionDTO> transactions;

    private AccountType accountType;

    private boolean isActive;
    public AccountDTO(Account account) {

        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions =  account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(toSet());
        this.isActive = account.isActive();
        this.accountType = account.getAccountType();

}

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }


    public long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public AccountType getAccountType() {return accountType;}
    public void setAccountType(AccountType accountType) {this.accountType = accountType;}
}
