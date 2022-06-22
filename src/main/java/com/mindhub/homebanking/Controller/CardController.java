package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.DTO.CardTransactionDTO;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.CardsService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.Utils.Utility;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class CardController {


    int cvv = Utility.getRandomNumber(0, 999);
    String cardNumber = Utility.getCardNumber();

    LocalDate hoy = LocalDate.now();
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardsService cardsService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;


    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.PATCH)
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam long id){

        Client client = clientService.getCurrent(authentication);
        Set<Card> cards = client.getCards();
        Card card = cardsService.findCard(id);

        if(!cards.contains(card)){
            new ResponseEntity<>("Invalid Card",HttpStatus.FORBIDDEN);
        }

        card.setActive(false);
        cardsService.saveCards(card);

        return new ResponseEntity<>("Card deleted", HttpStatus.ACCEPTED);
    }



    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam CardColor cardColor,
                                                  @RequestParam CardType cardType) {

        Client client = clientService.getCurrent(authentication);

        Set<Card> cards = client.getCards().stream().filter(card -> card.isActive()).collect(toSet());

        Set<Card> debit = cards.stream().filter(card -> card.getCardType() == CardType.DEBIT).collect(toSet());

        Set<Card> credit = cards.stream().filter(card -> card.getCardType() == CardType.CREDIT).collect(toSet());


        if( cardType != CardType.CREDIT && cardType != CardType.DEBIT){
            return new ResponseEntity<>("invalid cardType", HttpStatus.FORBIDDEN);
        }
        if (cardColor != CardColor.SILVER && cardColor != CardColor.GOLD && cardColor != CardColor.TITANIUM){
            return new ResponseEntity<>("Invalid Card Color", HttpStatus.FORBIDDEN);
        }

        if (cardType == CardType.DEBIT && debit.size() >= 3) {
            return new ResponseEntity<>("Limite de tarjetas de debito alcanzado", HttpStatus.FORBIDDEN);
        }

        if (cardType == CardType.CREDIT && credit.size() >= 3) {
            return new ResponseEntity<>("Limite de tarjetas de credito alcanzado", HttpStatus.FORBIDDEN);
        }

        Card card = new Card(client, cardType, cardColor, LocalDate.now(), hoy.plusYears(5), cvv,
                cardNumber);
        cardsService.saveCards(card);

        return new ResponseEntity<>("Nueva tarjeta creada", HttpStatus.CREATED);
    }


    @Transactional
    @PostMapping("/clients/current/cardTransaction")
    public ResponseEntity<Object> cardPayment(Authentication authentication, @RequestBody CardTransactionDTO cardTransactionDTO){

        Client client = clientService.getCurrent(authentication);
        Card card = cardsService.findByNumber(cardTransactionDTO.getCardNumber());
        List<Account> accounts = client.getAccounts().stream().collect(Collectors.toList());

        accounts = accounts.stream().sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());

        Account account = accounts.stream().findFirst().orElse(null);

        if(cardTransactionDTO == null){
            return new ResponseEntity<>("invalid Data", HttpStatus.FORBIDDEN);
        }
        if (account == null){
            return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("invalid Account", HttpStatus.FORBIDDEN);
        }
        if (!client.getCards().contains(card)){
            return new ResponseEntity<>("Car does not belong to the client", HttpStatus.FORBIDDEN);
        }
        if (cardTransactionDTO.getAmount() > account.getBalance()){
            return new ResponseEntity<>("amount surpass account balance", HttpStatus.FORBIDDEN);
        }
        if (cardTransactionDTO.getCvv() != card.getCvv()){
            return new ResponseEntity<>("Invalid security code", HttpStatus.FORBIDDEN);
        }
        if (card.getThruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("Expired Card", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(account, TransactionType.DEBITO, -cardTransactionDTO.getAmount(), cardTransactionDTO.getDescription(), LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance()-cardTransactionDTO.getAmount());
        accountService.saveAccount(account);

        return new ResponseEntity<>("Payment Accepted", HttpStatus.ACCEPTED);
    }

}
