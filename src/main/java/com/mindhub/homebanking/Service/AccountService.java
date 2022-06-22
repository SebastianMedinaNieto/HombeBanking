package com.mindhub.homebanking.Service;


import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;
import java.util.Set;

public interface AccountService {

    Account getAccountById(long id);
    Set<AccountDTO> getAccountsDTO();

    AccountDTO getAccountDTO(long id);

    void saveAccount(Account account);

    Account getAccountByNumber(String number);

    List<Account> getListAccounts();
}
