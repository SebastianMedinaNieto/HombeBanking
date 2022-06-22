package com.mindhub.homebanking.Controller;


import com.mindhub.homebanking.DTO.LoanAplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Service.*;
import com.mindhub.homebanking.models.*;

import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping(path = "/loans")
    public Set<LoanDTO> getAll(){
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping(path = "/loan")
    public ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDTO loanAplicationDTO,
                                          Authentication authentication){

        Client client = clientService.getCurrent(authentication);
        Account account = accountService.getAccountByNumber(loanAplicationDTO.getNumber());
        Loan loan = loanService.getLoan(loanAplicationDTO.getIdLoan());


        if (loanAplicationDTO.getAmount() == 0){
            return new ResponseEntity<>("Amount can not be 0", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getAmount() < 0){
            return new ResponseEntity<>("Amount can not be negative", HttpStatus.FORBIDDEN);
        }
        if (!account.isActive()){
            return new ResponseEntity<>("Invalid Account", HttpStatus.FORBIDDEN);
        }
        if(loanAplicationDTO.getPayments() <= 0 ){
            return new ResponseEntity<>("Payments can not be 0 or negative", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("Account missing", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO == null){
            return new ResponseEntity<>("Invalid Data", HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("Loan not Found", HttpStatus.FORBIDDEN);
        }
        if (accountService.getAccountByNumber(loanAplicationDTO.getNumber()) == null){
            return new ResponseEntity<>("Account not Found", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Account Invalid", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayment().contains(loanAplicationDTO.getPayments())){
            return new ResponseEntity<>("Invalid Payments", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("Max amount exceeded", HttpStatus.FORBIDDEN);
        }


        ClientLoan clientLoan = new ClientLoan(client, loan, loanAplicationDTO.getAmount()*(loan.getInterest() + 1),loanAplicationDTO.getPayments());
        clientLoanService.saveClientLoan(clientLoan);

        Transaction transaction = new Transaction(account, TransactionType.CREDITO, loanAplicationDTO.getAmount(),
                "loan Approved"+" " +loan.getName(), LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance()+loanAplicationDTO.getAmount());
        accountService.saveAccount(account);


        return new ResponseEntity<>("Loan Approved", HttpStatus.CREATED);
    }




    @PostMapping("/admin/loan")
    public ResponseEntity<Object> newLoan(Authentication authentication, @RequestParam double maxAmount, @RequestParam String name,
                                          @RequestParam double interest, @RequestParam List<Integer> payments){

        Client admin = clientService.getCurrent(authentication);

        if(!admin.getEmail().contains("@admin")){
            new ResponseEntity<>("Only admin funtion", HttpStatus.FORBIDDEN);
        }
        if (maxAmount >= 0){
            new ResponseEntity<>("Amount can not be 0 or less", HttpStatus.FORBIDDEN);
        }
        if(name.isEmpty()){
            new ResponseEntity<>("Name can not be empty", HttpStatus.FORBIDDEN);
        }
        if (interest >= 0){
            new ResponseEntity<>("Interest can not be 0 or less", HttpStatus.FORBIDDEN);
        }
        if (payments.size() <= 0){
            new ResponseEntity<>("Need payments", HttpStatus.FORBIDDEN);
        }
        if (maxAmount > 500000){
            new ResponseEntity<>("Max amount exceded", HttpStatus.FORBIDDEN);
        }
        if(loanService.getByName(name) != null) {
            new ResponseEntity<>("Name in use", HttpStatus.FORBIDDEN);
        }
        if (interest >= 1){
            new ResponseEntity<>("Interest too high", HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(name, maxAmount, payments, interest);
        loanService.loanSave(loan);

        return new ResponseEntity<>("Loan created", HttpStatus.ACCEPTED);
    }

}
