package com.rjtech.mw.error.resp.message;

import java.io.Serializable;

public class AppErrorMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4281379808766399220L;
    private String code;
    private String message;
    private String errorType = "S";

    public AppErrorMessage(String code, String message, String errorType) {
        super();
        this.code = code;
        this.message = message;
        this.errorType = errorType;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorType() {
        return errorType;
    }

}
