package com.rjtech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class POTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger log = LoggerFactory.getLogger(POTAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        log.info("   POTAuthenticationEntryPoint From   ");

    }

}
