package com.okomski.loanservice.validators.exceptions;

import java.util.List;

public class ApplicationRejected extends RuntimeException {
    private final List<String> errors;
    public ApplicationRejected(List<String> errors) {
     this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
