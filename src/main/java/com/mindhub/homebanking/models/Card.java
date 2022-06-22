package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cardHolder_id")
    private Client cardHolder;

    private CardType cardType;

    private CardColor cardColor;

    private LocalDate fromDate;

    private LocalDate thruDate;

    private int cvv;

    private boolean isActive;

    private String number;


    public Card() {
    }

    public Card(Client cardHolder, CardType cardType, CardColor cardColor, LocalDate fromDate,
                LocalDate thruDate, int cvv, String number) {

        this.cardHolder = cardHolder;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cvv = cvv;
        this.number = number;
        this.isActive = true;

    }

    public Client getCardHolder() {return cardHolder;}
    public void setCardHolder(Client cardHolder) {this.cardHolder = cardHolder;}

    public CardType getCardType() {return cardType;}
    public void setCardType(CardType cardType) {this.cardType = cardType;}

    public CardColor getCardColor() {return cardColor;}
    public void setCardColor(CardColor cardColor) {this.cardColor = cardColor;}

    public LocalDate getFromDate() {return fromDate;}
    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getThruDate() {return thruDate;}
    public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}

    public int getCvv() {return cvv;}
    public void setCvv(int cvv) {this.cvv = cvv;}

    public String getNumber() {return number;}

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setNumber(String number) {this.number = number;}


    public long getId() {
        return id;
    }


}
