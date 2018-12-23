package com.okomski.loanservice.validators.rule;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(TermMinMaxRule.BEAN_NAME)
public class TermMinMaxRule implements ValidatableAndMessagable<LoanApplicationRequest> {
    public static final String BEAN_NAME = "termMinMaxRule";


    public static LocalDateTime MIN_TERM = LocalDateTime.now().minusDays(3);
    public static LocalDateTime MAX_TERM = LocalDateTime.now().plusDays(3);

    @Autowired
    CommonBusinessRuleProperties commonBusinessRuleProperties;

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest) {
        LocalDateTime term = loanApplicationRequest.getTerm();
        return term.isAfter(MIN_TERM) &&
                term.isBefore(MAX_TERM);
    }

    @Override
    public String getErrorMessage() {
        return "Term is not valid";
    }
}
