package com.rjtech.pot.authentication.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.rjtech.rjs.appuser.utils.ApplicationAuthConstants;
import com.rjtech.rjs.service.token.RJSTokenService;

/**
 * Bean For handling logout success.
 */
@Component
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);

    @Autowired
    @Qualifier("rjsTokenService")
    private RJSTokenService rjsTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String token = request.getHeader(ApplicationAuthConstants.POTTOKEN);
        if (token != null)
            rjsTokenService.delete(token);
    }

}
