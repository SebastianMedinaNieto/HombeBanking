package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private Client owner;

    @OneToMany(mappedBy="account_id", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();


    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean isActive;

    private AccountType accountType;



    public Account (){};

    public Account(Client owner, String number, LocalDateTime creationDate, double balance, AccountType accountType) {
        this.owner = owner;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.isActive = true;
        this.accountType = accountType;
    }

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public LocalDateTime getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}


    public long getId() {return id;}

    public Client getOwner() {return owner;}


    public void setOwner(Client client) {
    }

    public void addTransactions(Transaction transaction) {
        transaction.setAccount_id(this);
        transactions.add(transaction);  //la s es por el set declarado arriba
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
