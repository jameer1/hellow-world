
package com.rjtech.rjs.compression.exception;

public class CompressionServiceException extends Exception {

    private static final long serialVersionUID = 8018322587171957561L;

    private String message;

    public CompressionServiceException(String errorMessage) {
        super(errorMessage);
        this.message = errorMessage;

    }

    public CompressionServiceException(String errorMessage, Throwable cause) {
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
