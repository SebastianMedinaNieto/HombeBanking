package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


     private String name;
     private double maxAmount;

    @ElementCollection
    @Column(name="Payments")
    private List<Integer> payment = new ArrayList<>();

    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    private double interest;



    public Loan() {
    }


    public Loan(String name, double maxAmount, List<Integer> payment, double interest) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payment = payment;
        this.interest = interest;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getMaxAmount() {return maxAmount;}
    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayment() {return payment;}
    public void setPayment(List<Integer> payment) {this.payment = payment;}

    public long getId() {return id;}

    public Set<ClientLoan> getClientLoans(){ return clientLoans;}

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);

    }
    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public List<Loan> getLoans(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());
    }

    public double getInterest() {return interest;}
    public void setInterest(double interest) {this.interest = interest;}
}
