package com.rjtech.rjs.persistence.exception;

import java.util.Arrays;

@SuppressWarnings("serial")
public class RJSMultipleRJSEntityManagerFactoryConfiguredException extends RuntimeException {

    private String reason;

    public RJSMultipleRJSEntityManagerFactoryConfiguredException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RJSMultipleRJSEntityManagerFactoryConfiguredException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        this.reason = arg0;
        // TODO Auto-generated constructor stub
    }

    public RJSMultipleRJSEntityManagerFactoryConfiguredException(String arg0) {
        super(arg0);
        this.reason = arg0;
        // TODO Auto-generated constructor stub
    }

    public RJSMultipleRJSEntityManagerFactoryConfiguredException(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "RJSMultipleRJSEntityManagerFactoryConfiguredException [reason=" + reason + ", getMessage()="
                + getMessage() + ", getLocalizedMessage()=" + getLocalizedMessage() + ", getCause()=" + getCause()
                + ", toString()=" + super.toString() + ", fillInStackTrace()=" + fillInStackTrace()
                + ", getStackTrace()=" + Arrays.toString(getStackTrace()) + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + "]";
    }

}
