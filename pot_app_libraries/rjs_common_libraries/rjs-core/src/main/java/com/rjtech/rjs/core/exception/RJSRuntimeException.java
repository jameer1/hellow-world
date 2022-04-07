package com.rjtech.rjs.core.exception;

public class RJSRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    String reason;

    public RJSRuntimeException() {
        super();
    }

    public RJSRuntimeException(String message) {
        super(message);
        this.reason = message;
    }

    public RJSRuntimeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        this.reason = arg0;
    }

    public RJSRuntimeException(Throwable arg0) {
        super(arg0);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reason == null) ? 0 : reason.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RJSRuntimeException other = (RJSRuntimeException) obj;
        if (reason == null) {
            if (other.reason != null) {
                return false;
            }
        } else if (!reason.equals(other.reason)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RJSRuntimeException [reason=" + reason + "]";
    }

}
