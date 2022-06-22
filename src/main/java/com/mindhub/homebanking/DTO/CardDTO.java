package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private long id;

    private String cardHolder;

    private CardType cardType;

    private CardColor cardColor;

    private LocalDate fromDate;

    private LocalDate thruDate;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private int cvv;

    private String number;

    private boolean isActive;



    public CardDTO(Card card) {

        this.id = card.getId();
        this.cardHolder = card.getCardHolder().getFirstName() +" "+ card.getCardHolder().getLastName();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cvv = card.getCvv();
        this.number = card.getNumber();
        this.isActive = card.isActive();




    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
