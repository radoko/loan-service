package com.okomski.loanservice.validators.rule;

public interface ValidatableAndMessagable<T> {
    boolean isValid(T input);
    String getErrorMessage();
}
