package com.rjtech.rjs.persistence.exception;

@SuppressWarnings("serial")
public class RJSIDGeneratorNotFoundException extends RuntimeException {
    private String reason;

    public RJSIDGeneratorNotFoundException() {
        super();
    }

    public RJSIDGeneratorNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RJSIDGeneratorNotFoundException(String arg0) {
        super(arg0);
    }

    public RJSIDGeneratorNotFoundException(Throwable arg0) {
        super(arg0);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
