package com.rjtech.mw.dto.login;

import java.io.Serializable;

public class LoginRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7942412135031674725L;

    private String username;
    private String password;
    private String clientCode;

    public LoginRequest(String username, String password, String clientCode) {
        super();
        this.username = username;
        this.password = password;
        this.clientCode = clientCode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClientCode() {
        return clientCode;
    }

}
