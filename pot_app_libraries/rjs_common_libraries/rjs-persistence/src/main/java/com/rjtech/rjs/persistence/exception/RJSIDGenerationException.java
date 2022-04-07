package com.rjtech.rjs.persistence.exception;

@SuppressWarnings("serial")
public class RJSIDGenerationException extends RuntimeException {

    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RJSIDGenerationException(String reason) {
        super();
        this.reason = reason;
    }

    public RJSIDGenerationException() {
        super();
    }

    public RJSIDGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RJSIDGenerationException(Throwable cause) {
        super(cause);
    }

}
