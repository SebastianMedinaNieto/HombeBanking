package com.mindhub.homebanking.DTO;


public class LoanAplicationDTO {



    private long idLoan;

    private double amount;

    private Integer payment;

    private String number;




    public LoanAplicationDTO() {
    }


    public LoanAplicationDTO(long idLoan, double amount, Integer payments, String number) {
        this.idLoan = idLoan;
        this.amount = amount;
        payment = payments;
        this.number = number;
    }


    public long getIdLoan() {return idLoan;}
    public void setLoanId(long idLoan) {this.idLoan = idLoan;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public Integer getPayments() {return payment;}
    public void setPayments(Integer payments) {payment = payments;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}
}
