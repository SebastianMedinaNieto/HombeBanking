package com.mindhub.homebanking.DTO;


import java.time.LocalDate;

public class CardTransactionDTO {

    private String cardNumber;

    private int cvv;

    private double amount;

    private String description;




    public CardTransactionDTO(String cardNumber, int cvv, double amount, String description) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;

    }



    public String getCardNumber() {return cardNumber;}
    public void setCardNumber(String cardNumber) {this.cardNumber = cardNumber;}

    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
