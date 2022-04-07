package com.rjtech.mw.app.exception;

import java.io.Serializable;

public class AppMWException extends RuntimeException implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -549849244339009254L;

    public AppMWException(String code, String[] args, String message, String errorType) {
        super();
        this.code = code;
        this.args = args;
        this.message = message;
        this.errorType = errorType;

    }

    public AppMWException(String code, String message) {
        super();
        this.code = code;
        this.message = message;

    }

    public AppMWException(String message) {
        super();
        this.message = message;

    }

    private String code;
    private String message;
    private String errorType = "S";
    private String[] args;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

}
