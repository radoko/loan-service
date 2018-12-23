package com.okomski.loanservice.validators.rule;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(MaxAmountAllowedRule.BEAN_NAME)
public class MaxAmountAllowedRule implements ValidatableAndMessagable<LoanApplicationRequest> {
    public static final String BEAN_NAME = "maxAmountAsked";

    @Autowired
    CommonBusinessRuleProperties commonBusinessRuleProperties;

    private static LocalTime MIN_ASKED_HOUR = LocalTime.MIDNIGHT;
    private static LocalTime MAX_ASKED_TIME = LocalTime.of(6,0);

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest) {
        LocalTime currentTime = getCurrentTime();
        BigDecimal amount = loanApplicationRequest.getAmount();

        return !(BigDecimalUtils.equals(amount, commonBusinessRuleProperties.getMaxAmount()) &&
                currentTime.isAfter(MIN_ASKED_HOUR) && currentTime.isBefore(MAX_ASKED_TIME));
    }

    @Override
    public String getErrorMessage() {
        return "Max amount is not available now";
    }

    private LocalTime getCurrentTime() {
            return LocalTime.now();
    }
}
