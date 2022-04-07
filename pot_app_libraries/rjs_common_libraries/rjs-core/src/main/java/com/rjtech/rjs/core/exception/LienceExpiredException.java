package com.rjtech.rjs.core.exception;

public class LienceExpiredException extends RuntimeException {
    private static final long serialVersionUID = 8018322587171957885L;

    private String message;

    public LienceExpiredException(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;
    }

    public LienceExpiredException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}