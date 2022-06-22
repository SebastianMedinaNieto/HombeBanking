package com.mindhub.homebanking.Service;


import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Set;

public interface LoanService {


    Set<LoanDTO> getLoans();

    Set<Loan> getAllLoans();

    Loan getByName(String name);

    void loanSave(Loan loan);

    Loan getLoan(long id);
}
