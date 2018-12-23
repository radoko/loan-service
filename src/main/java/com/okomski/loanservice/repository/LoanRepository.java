package com.okomski.loanservice.repository;

import com.okomski.loanservice.repository.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {
}
