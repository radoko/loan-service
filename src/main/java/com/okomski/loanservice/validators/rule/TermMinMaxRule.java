package com.okomski.loanservice.validators.rule;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.utils.SystemClock;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(TermMinMaxRule.BEAN_NAME)
public class TermMinMaxRule implements ValidatableAndMessagable<LoanApplicationRequest> {
    public static final String BEAN_NAME = "termMinMaxRule";

    @Autowired
    SystemClock systemClock;

    @Autowired
    CommonBusinessRuleProperties commonBusinessRuleProperties;

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest) {
        Integer days = commonBusinessRuleProperties.getMaxValidTermInDays();

        LocalDateTime MIN_TERM = systemClock.localDateNow().atStartOfDay();
        LocalDateTime MAX_TERM = systemClock.localDateTimeNow().plusDays(days);

        LocalDateTime term = loanApplicationRequest.getTerm();
        return term.isAfter(MIN_TERM) &&
                term.isBefore(MAX_TERM);
    }

    @Override
    public String getErrorMessage() {
        return "Term is not valid";
    }
}
