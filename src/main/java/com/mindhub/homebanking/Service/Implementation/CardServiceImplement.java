package com.mindhub.homebanking.Service.Implementation;


import com.mindhub.homebanking.Service.CardsService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardsService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCards(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Card findCard(long id) {
        return cardRepository.findById(id).orElse(null);
    }
}
