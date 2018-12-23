package com.okomski.loanservice.service;

import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.rest.dto.LoanApplicationResponse;

public interface LoanService {
    void extendLoan(Long loanId);
    LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplication);
}
