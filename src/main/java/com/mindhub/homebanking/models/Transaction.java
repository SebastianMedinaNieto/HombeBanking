package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account_id;

    private double accountBalance;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime creationDate;

    private boolean isActive;


    public Transaction (){};

    public Transaction(Account account_id, TransactionType type, double amount, String description, LocalDateTime creationDate) {
        this.account_id = account_id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.creationDate = creationDate;
        this.accountBalance = account_id.getBalance() + amount;
        this.isActive = true;
    }

// getterns and setters abajo, abran camino //

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}


    public long getId() {return id;}

    public void setAccount_id(Account account) {}

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
