package com.rjtech.pot.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;

public class POTTokenExpiredException extends AuthenticationException {

    public POTTokenExpiredException(String msg) {
        super(msg);
    }

}
