package com.okomski.loanservice.validators;

import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.validators.rule.AmountMinMaxRule;
import com.okomski.loanservice.validators.rule.MaxAmountAllowedRule;
import com.okomski.loanservice.validators.rule.TermMinMaxRule;
import com.okomski.loanservice.validators.rule.ValidatableAndMessagable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoanApplicationValidationContext {
    private Set<ValidatableAndMessagable> validators;

    private static final List<String> VALIDATION_RULE_NAME = Arrays.asList(
            AmountMinMaxRule.BEAN_NAME,
            MaxAmountAllowedRule.BEAN_NAME,
            TermMinMaxRule.BEAN_NAME);

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void initValidators() {
        validators = new HashSet<>();
        VALIDATION_RULE_NAME.forEach(beanName ->
                validators.add((ValidatableAndMessagable) applicationContext.getBean(beanName)));
    }

    public List<String> validateAndGetErrors(LoanApplicationRequest loanApplicationRequest) {
        List<String> errors = new ArrayList<>();
        for (ValidatableAndMessagable validator : validators) {
            if (!validator.isValid(loanApplicationRequest)) {
                errors.add(validator.getErrorMessage());
            }
        }

        return errors;
    }
}
