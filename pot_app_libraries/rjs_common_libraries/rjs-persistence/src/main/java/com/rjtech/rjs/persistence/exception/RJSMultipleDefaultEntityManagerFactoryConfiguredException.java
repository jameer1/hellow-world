package com.rjtech.rjs.persistence.exception;

@SuppressWarnings("serial")
public class RJSMultipleDefaultEntityManagerFactoryConfiguredException extends RuntimeException {

    private String reason;

    public RJSMultipleDefaultEntityManagerFactoryConfiguredException(String reason) {
        super();
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RJSMultipleDefaultEntityManagerFactoryConfiguredException [reason=" + reason + "]";
    }

}
