package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {


    private long id;

    private TransactionType type;

    private double amount;
    private double accountAmount;

    private String description;

    private LocalDateTime creationDate;



    private boolean isActive;


    public TransactionDTO (Transaction transaction) {

        this.id = transaction.getId();

        this.type = transaction.getType();

        this.amount = transaction.getAmount();

        this.description = transaction.getDescription();

        this.creationDate = transaction.getCreationDate();

        this.accountAmount = transaction.getAccountBalance();

        this.isActive = transaction.isActive();

    }


    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public TransactionType getType() {return type;}
    public void setType(TransactionType type) {this.type = type;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {isActive = active;}

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }
}
