package com.mindhub.homebanking.Service.Implementation;


import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;


    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Set<AccountDTO> getAccountsDTO() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    @Override
    public AccountDTO getAccountDTO(long id) {
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public List<Account> getListAccounts() {
        return accountRepository.findAll();
    }
}
