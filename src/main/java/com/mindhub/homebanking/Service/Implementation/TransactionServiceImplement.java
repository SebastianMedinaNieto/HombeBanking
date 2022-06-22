package com.mindhub.homebanking.Service.Implementation;


import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Set<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
