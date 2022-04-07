package com.rjtech.pot.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class POTAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 5848754296869369943L;
    private Object clientcode;
    private Object token;
    private final Object principal;
    private Object credentials;

    public POTAuthenticationToken(Object principal, Object credentials, Object clientcode, Object token) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.clientcode = clientcode;
        this.token = token;
        setAuthenticated(false);

    }

    public POTAuthenticationToken(Object principal, Object credentials, Object clientcode, Object token,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.clientcode = clientcode;
        this.token = token;
        super.setAuthenticated(true); // must use super, as we override

    }

    public Object getClientcode() {
        return clientcode;
    }

    public Object getToken() {
        return token;
    }

    public Object getPrincipal() {
        return principal;
    }

    public Object getCredentials() {
        return credentials;
    }

    public void setClientcode(Object clientcode) {
        this.clientcode = clientcode;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

}
