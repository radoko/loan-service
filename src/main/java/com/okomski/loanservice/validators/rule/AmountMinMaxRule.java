package com.okomski.loanservice.validators.rule;

import com.okomski.loanservice.config.CommonBusinessRuleProperties;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.utils.BigDecimalUtils;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(AmountMinMaxRule.BEAN_NAME)
public class AmountMinMaxRule implements ValidatableAndMessagable<LoanApplicationRequest> {
    public static final String BEAN_NAME = "amountMinMaxRule";

    @Autowired
    CommonBusinessRuleProperties commonBusinessRuleProperties;

    @Override
    public boolean isValid(LoanApplicationRequest loanApplicationRequest) {
        BigDecimal minAmount = commonBusinessRuleProperties.getMinAmount();
        BigDecimal maxAmount = commonBusinessRuleProperties.getMaxAmount();
        BigDecimal amount = loanApplicationRequest.getAmount();

        return BigDecimalUtils.greaterThanOrEqual(amount, minAmount) &&
                BigDecimalUtils.lessThanOrEqual(amount, maxAmount);
    }

    @Override
    public String getErrorMessage() {
        return "Amount is not valid";
    }
}
