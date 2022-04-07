package com.rjtech.pot.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthEntryPoint extends BasicAuthenticationEntryPoint {

    public TokenAuthEntryPoint() {
        super.setRealmName("CORP.RJTECH.COM");
    }

    @Override
    public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
            throws IOException, ServletException {

    }

}
