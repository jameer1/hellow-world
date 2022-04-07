
package com.rjtech.pot.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.ApplicationAuthConstants;

public class SignInAuthenticationFilter extends BasicAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public SignInAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInAuthenticationFilter.class);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        requestURI = StringUtils.substringAfter(requestURI, contextPath);

        if (request.getMethod().equalsIgnoreCase("OPTIONS") && !StringUtils.equals("/", requestURI)) {
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Headers",
                    "Accept,Accept-Encoding,Accept-Language,Cache-Control,Connection,Content-Length,Content-Type,x-csrf-token"
                            + "Cookie,Host,Pragma,Referer,RemoteQueueID,User-Agent,contenttype,pottoken,x-pottoken,headers");
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Methods", "GET,  POST");
            res.setStatus(200);
            return;
        }
        
        String token = request.getHeader(ApplicationAuthConstants.POTTOKEN);
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(ApplicationAuthConstants.POTTOKEN)) {
                    token = cookie.getValue();
                }

            }

        }
        final SecurityContext context = SecurityContextHolder.getContext();

        LOGGER.info(
                " Admin  SignInAuthenticationFilter  ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: doFilterInternal entry"
                        + token);

        if (CommonUtil.isNotBlankStr(token)) {

            Authentication authentication = authenticationManager.authenticate(new SignInAuthenticationToken(token));
            context.setAuthentication(authentication);
        }
        LOGGER.info(
                " Admin MW SignInAuthenticationFilter  ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: doFilterInternal exist ");
        super.doFilterInternal(request, res, chain);
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
