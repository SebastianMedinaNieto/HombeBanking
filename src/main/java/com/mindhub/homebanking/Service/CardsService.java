package com.mindhub.homebanking.Service;


import com.mindhub.homebanking.models.Card;

public interface CardsService {

    void saveCards(Card card);

    Card findByNumber(String number);
    Card findCard(long id);

}
