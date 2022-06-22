package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {


    private long id;

    private double amount;

    private Integer Payments;

    private String name;

    private long loanid;

    public ClientLoanDTO(ClientLoan clientLoan) {

        this.id = clientLoan.getId();
        this.loanid = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount() ;
        this.Payments = clientLoan.getPayments();


    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public Integer getPayments() {return Payments;}
    public void setPayments(Integer payments) {Payments = payments;}


    public long getLoanid() {
        return loanid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoanid(long loanid) {
        this.loanid = loanid;
    }
}
