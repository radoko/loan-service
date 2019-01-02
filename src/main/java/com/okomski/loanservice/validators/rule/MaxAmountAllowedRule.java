package com.okomski.loanservice.validators.rule;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.utils.BigDecimalUtils;
import com.okomski.loanservice.utils.ClockUtil;
import com.okomski.loanservice.utils.SystemClock;
import java.math.BigDecimal;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(MaxAmountAllowedRule.BEAN_NAME)
public class MaxAmountAllowedRule implements ValidatableAndMessagable<LoanApplicationRequest> {
    public static final String BEAN_NAME = "maxAmountAsked";

    @Autowired
    CommonBusinessRuleProperties commonBusinessRuleProperties;

    @Autowired
    SystemClock systemClock;

    private static LocalTime MIN_ASKED_TIME = LocalTime.MIDNIGHT;
    private static LocalTime MAX_ASKED_TIME = LocalTime.of(6,0);

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest) {
        LocalTime currentTime = systemClock.localTime();
        BigDecimal amount = loanApplicationRequest.getAmount();
        BigDecimal maxAmount = commonBusinessRuleProperties.getMaxAmount();

        return BigDecimalUtils.lessThan(amount, maxAmount) ||
                (BigDecimalUtils.equals(amount, maxAmount) &&
                        ClockUtil.isAfterOrEqual(currentTime, MIN_ASKED_TIME) &&
                        ClockUtil.isBeforeOrEqual(currentTime, MAX_ASKED_TIME));
    }

    @Override
    public String getErrorMessage() {
        return "Max amount is not available now";
    }

}
