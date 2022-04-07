package com.rjtech.rjs.validation.exception;

import java.util.List;

import javax.validation.ConstraintViolation;

public class RJSValidationException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -1365845931579301781L;
    private List<ConstraintViolation<?>> violations;

    public RJSValidationException(List<ConstraintViolation<?>> violations) {
        this.violations = violations;
    }

    public RJSValidationException(String s, List<ConstraintViolation<?>> violations) {
        super(s);
        this.violations = violations;
    }

    public RJSValidationException(String message, Throwable throwable, List<ConstraintViolation<?>> violations) {
        super(message, throwable);
        this.violations = violations;
    }

    public List<ConstraintViolation<?>> getViolations() {
        return violations;
    }
}
