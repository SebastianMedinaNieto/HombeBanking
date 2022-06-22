package com.mindhub.homebanking.Service;


import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionService {

    Set<TransactionDTO> getTransactions();

    void saveTransaction(Transaction transaction);

}
