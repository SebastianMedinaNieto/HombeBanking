package com.mindhub.homebanking.Service.Implementation;


import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Service.LoanService;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public Set<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toSet());
    }

    @Override
    public Set<Loan> getAllLoans() {
        return loanRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public Loan getByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public void loanSave(Loan loan) {
        loanRepository.save(loan);

    }

    @Override
    public Loan getLoan(long id) {
        return loanRepository.findById(id);
    }
}
