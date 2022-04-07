package com.rjtech.pot.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SignInAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final Object principal;

    private String pottoken;

    public SignInAuthenticationToken(String potToken) {
        super(null);
        this.pottoken = potToken;
        this.principal = null;

    }

    public Object getCredentials() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getPrincipal() {

        return this.principal;
    }

    public String getPottoken() {
        return pottoken;
    }

    public void setPottoken(String pottoken) {
        this.pottoken = pottoken;
    }

}
