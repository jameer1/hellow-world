package com.rjtech.rjs.persistence.exception;

@SuppressWarnings("serial")
public class RJSMultipleIDGeneratorPresentException extends RuntimeException {

    private String reason;

    public RJSMultipleIDGeneratorPresentException(String reason) {
        super();
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "RJSMultipleIDGeneratorPresentException [reason=" + reason + "]";
    }

}
