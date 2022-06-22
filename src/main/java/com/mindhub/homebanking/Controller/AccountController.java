package com.mindhub.homebanking.Controller;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.Utils.Utility;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import java.util.Set;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    String accountNumber = String.valueOf(Utility.getRandomNumber(00000000, 99999999));


    @GetMapping("/accounts")
    public Set<AccountDTO> getAll() {
        return accountService.getAccountsDTO();

    }


    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        return accountService.getAccountDTO(id);

    }




    @GetMapping("/clients/current/accounts")
    public Set<AccountDTO> getCurrent(Authentication authentication) {

       Client client = clientService.getCurrent(authentication);

       return client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());

    }

    @GetMapping("/clients/current/accounts/{id}")
    public AccountDTO getAccountID(@PathVariable long id, Authentication authentication){

        Client client = clientService.getCurrent(authentication);

        Account account = accountService.getAccountById(id);

        if(!client.getAccounts().contains(account)){
            return null;
        }
        if (account.isActive() == false){
            return null;
        }

        return new AccountDTO(account);

    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam AccountType accountType){

        Client client = clientService.getCurrent(authentication);
        Set<Account> accounts = client.getAccounts().stream().filter(account -> account.isActive() == true).collect(Collectors.toSet());

        if (accounts.size() >= 3){
            return new ResponseEntity<>("Reached limit of accounts (3)", HttpStatus.FORBIDDEN);
        }



        Account account = new Account(client,"VIN"+accountNumber, LocalDateTime.now() ,0, accountType);
        accountService.saveAccount(account);


        return new ResponseEntity<>("Nueva cuenta creada", HttpStatus.CREATED);

    }

    @PatchMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam Long id) {

        Client client = clientService.getCurrent(authentication);
        Account account = accountService.getAccountById(id);
        Set<Transaction> transactions = account.getTransactions();

        if (account.getBalance() > 0) {
            return new ResponseEntity<>("Can not delete an account with money", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("invalid Account", HttpStatus.FORBIDDEN);
        }
        if (!account.isActive()) {
            return new ResponseEntity<>("account does not exist", HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountService.saveAccount(account);

        transactions.forEach(transaction -> transaction.setActive(false));
        transactions.forEach(transaction -> transactionService.saveTransaction(transaction));

        return new ResponseEntity<>("Account Deleted", HttpStatus.ACCEPTED);
    }
}
