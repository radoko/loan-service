package com.okomski.loanservice.service;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.rest.dto.LoanApplicationResponse;
import com.okomski.loanservice.repository.model.Loan;
import com.okomski.loanservice.repository.model.LoanEvent;
import com.okomski.loanservice.repository.model.LoanEventType;
import com.okomski.loanservice.repository.LoanRepository;
import com.okomski.loanservice.utils.BigDecimalUtils;
import com.okomski.loanservice.validators.LoanApplicationValidationContext;
import com.okomski.loanservice.validators.exceptions.ApplicationRejected;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    public static final String DEFAULT_CURRENCY = "PL";
    public static final BigDecimal INTEREST_PERCENTAGE_CONFIG = new BigDecimal(10);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanApplicationValidationContext validationContext;

    @Autowired
    private CommonBusinessRuleProperties commonBusinessRuleProperties;

    @Override
    @Transactional
    public void extendLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                new ResourceNotFoundException("Loan with id " + loanId + " not found"));

        LoanEvent extensionEvent = LoanEvent.builder()
                .eventDate(LocalDateTime.now())
                .eventType(LoanEventType.EXTENSION)
                .eventDescription("Due date extended by " + commonBusinessRuleProperties.getExtensionDays())
                .build();

        loan.setDueDate(loan.getDueDate().plusDays(commonBusinessRuleProperties.getExtensionDays()));
        loan.getEventHistory().add(extensionEvent);
    }

    @Transactional
    public LoanApplicationResponse applyForLoan(LoanApplicationRequest loanApplication) {
        List<String> errors = validationContext.validateAndGetErrors(loanApplication);
        if (!errors.isEmpty()) {
            throw new ApplicationRejected(errors);
        }
        List<LoanEvent> loanEvents = Arrays.asList(LoanEvent.builder()
                .eventDate(LocalDateTime.now())
                .eventType(LoanEventType.APPLICATION)
                .eventDescription("Loan application created")
                .build());

        Loan loan = Loan.builder()
                .loanAmount(loanApplication.getAmount())
                .loanCurrency(DEFAULT_CURRENCY)
                .loanInterest(BigDecimalUtils.percentage(loanApplication.getAmount(), INTEREST_PERCENTAGE_CONFIG))
                .originDueDate(loanApplication.getTerm())
                .dueDate(loanApplication.getTerm())
                .eventHistory(loanEvents)
                .build();

        loanRepository.save(loan);
        return LoanApplicationResponse.builder()
                .loanApplicationId(loan.getId())
                .dueDate(loan.getDueDate())
                .totalLoanAmount(loan.getTotalLoanAmount())
                .build();
    }
}
