package com.mindhub.homebanking.Controller;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@RestController //automaticamente crea la peticion rest
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/transactions")
    public Set<TransactionDTO> getAll() {
        return transactionService.getTransactions();

    }

    @Transactional
    @PostMapping(path = "/transaction")
    public ResponseEntity<Object> newTransaction(Authentication authentication, @RequestParam double amount,
                                                 @RequestParam String originAccount,
                                                 @RequestParam String destinationAccount, @RequestParam String description ){


        Client client = clientService.getCurrent(authentication);

        Account origen = accountService.getAccountByNumber(originAccount);

        Account destino = accountService.getAccountByNumber(destinationAccount);

        if (amount == 0 ) {
            return new ResponseEntity<>("Amount can not be 0", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty() ){
            return new ResponseEntity<>("Missin description", HttpStatus.FORBIDDEN);
        }
        if(!origen.isActive() || !destino.isActive()){
            return new ResponseEntity<>("Invalid Account", HttpStatus.FORBIDDEN);
        }
        if (destinationAccount.isEmpty()){
            return new ResponseEntity<>("Missin destination account", HttpStatus.FORBIDDEN);
        }
        if(originAccount.isEmpty()){
            return new ResponseEntity<>("Missing origin Account", HttpStatus.FORBIDDEN);
        }
        if(amount <=0){
            return new ResponseEntity<>("Invalid Amount", HttpStatus.FORBIDDEN);
        }
        if (origen == null ){
            return new ResponseEntity<>("Origin account not found", HttpStatus.FORBIDDEN);

        }
        if ( destino == null){
            return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
        }

        if (origen == destino) {
            return new ResponseEntity<>("Origen account and Destination account can not be the same", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(origen)){
            return new ResponseEntity<>("Missing account", HttpStatus.FORBIDDEN);
        }

        if (origen.getBalance() < amount){
            return new ResponseEntity<>("Non-sufficient funds", HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigen = new Transaction(origen, TransactionType.DEBITO, -amount, destinationAccount + description, LocalDateTime.now());
        transactionService.saveTransaction(transactionOrigen);

        Transaction transactionDestiny = new Transaction(destino, TransactionType.CREDITO, amount, originAccount + description, LocalDateTime.now());
        transactionService.saveTransaction(transactionDestiny);


        origen.setBalance(origen.getBalance() - amount) ;
        accountService.saveAccount(origen); ;

        destino.setBalance(destino.getBalance() + amount ) ;
        accountService.saveAccount(destino); ;

        return new ResponseEntity<>(HttpStatus.CREATED);

    };

}
