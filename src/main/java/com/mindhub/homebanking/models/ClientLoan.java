package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ClientLoan {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;


    private double amount;

    private Integer Payments;

    public ClientLoan() {}

    public ClientLoan(Client client, Loan loan, double amount, Integer payments) {
        this.client = client;
        this.loan = loan;
        this.amount = amount;
        Payments = payments;
    }


    public Client getClient() {return client;}

    public void setClient(Client client){

    };

    public Loan getLoan() {return loan;}
    public void setLoan(Loan loan) {this.loan = loan;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public Integer getPayments() {return Payments;}
    public void setPayments(Integer payments) {Payments = payments;}

    public long getId() {return id;}








}
