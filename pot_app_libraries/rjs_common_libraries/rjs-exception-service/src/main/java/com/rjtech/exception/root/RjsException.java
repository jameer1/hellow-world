package com.rjtech.exception.root;

import java.io.Serializable;

public class RjsException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 3329939159786539834L;

    public RjsException(String code, String[] args) {
        super();
        this.code = code;
        this.args = args;

    }

    public RjsException(String code, String message) {
        super();
        this.code = code;
        this.message = message;

    }

    public RjsException(String message) {
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
