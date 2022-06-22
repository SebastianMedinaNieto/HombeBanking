package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {

    private long id;

    private String name;

    private double maxAmount;

    private List<Integer> payment;

    private double interest;



    public LoanDTO(Loan loan){

        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payment = loan.getPayment();
        this.interest = loan.getInterest();
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public double getMaxAmount() {return maxAmount;}

    public List<Integer> getPayment() {return payment;}

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayment(List<Integer> payment) {
        this.payment = payment;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }
}
