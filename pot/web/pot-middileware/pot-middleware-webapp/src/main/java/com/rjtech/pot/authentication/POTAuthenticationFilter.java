package com.rjtech.pot.authentication;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.pot.authentication.exceptions.POTTokenExpiredException;
import com.rjtech.rjs.appuser.utils.ApplicationAuthConstants;

public class POTAuthenticationFilter extends BasicAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public POTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(POTAuthenticationFilter.class);

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String clientcode = obtainCmpcode(request);

        String token = request.getHeader(ApplicationAuthConstants.POTTOKEN);
        // For file download we need to call GET method from browser, so pass pottoken as query parameter
        if (request.getMethod().equals(HttpMethod.GET.toString()) && token == null) {
            token = request.getParameter(ApplicationAuthConstants.POTTOKEN);
        }

        /*	 Boolean isTokenExistInCookie = false;
         if (token == null){
        	 LOGGER.info(" cookies   :::::::::::::;  "+request.getCookies());
        	 Cookie[] cookies = request.getCookies();
        	 LOGGER.info(" cookies   :::::::::::::;  "+cookies);
        	if (cookies != null && cookies.length> 0){
        		 for (Cookie cookie : cookies){
        			 if (cookie.getName().equalsIgnoreCase(ApplicationAuthConstants.POTTOKEN)){
        				 token = cookie.getValue();
        				 isTokenExistInCookie = true;
        			 }
        			 
        		 }
        	}
        	 
         }*/

        boolean doAuthentication = false;

        if ((CommonUtil.isNotBlankStr(username) && CommonUtil.isNotBlankStr(password))
                || (CommonUtil.isNotBlankStr(username) && CommonUtil.isNotBlankStr(password)
                        && CommonUtil.isNotBlankStr(clientcode))
                || CommonUtil.isNotBlankStr(token)) {
            doAuthentication = true;
        }

        // if token, uname not present in request then send unauthorized response
        if (!request.getMethod().equalsIgnoreCase("OPTIONS") && !doAuthentication && CommonUtil.isBlankStr(token)) {
            logger.info("Token is null, sending 401 unauthorised response");
            setUnauthorizedResponse(response);
            return;
        }

        if (doAuthentication) {
            // if token is present but session is expired then send unauthorized response
            if (CommonUtil.isNotBlankStr(token) && request.getCookies() != null) {
                Cookie jSessionCookie = Arrays.stream(request.getCookies())
                        .filter(x -> x.getName().equals("JSESSIONID")).findFirst().get();
                if (jSessionCookie != null && request.getSession(false) == null) {
                    logger.info("Session expired, sending 401 unauthorised response");
                    setUnauthorizedResponse(response);
                    return;
                }
            }
            POTAuthenticationToken authRequest = new POTAuthenticationToken(username, password, clientcode, token);

            Authentication auth = null;
            try {
                auth = this.getAuthenticationManager().authenticate(authRequest);
                final SecurityContext context = SecurityContextHolder.getContext();
                if (auth.isAuthenticated()) {
                    context.setAuthentication(auth);
                }
            } catch (POTTokenExpiredException potTokenExpiredException) {
                LOGGER.info("Token expired, setting unauthorized response status ");
                setUnauthorizedResponse(response);
                return;
            }

            /*	if (!isTokenExistInCookie && auth.isAuthenticated()){
            	 	Cookie pottoken = new Cookie(ApplicationAuthConstants.POTTOKEN, ((AppUserDetails)auth.getPrincipal()).getToken());
            	 	pottoken.setPath("/");
            		 	pottoken.setMaxAge(-1);
            		 	pottoken.setHttpOnly(true);
            		 	response.addCookie(pottoken);
            		}*/

        }

        super.doFilterInternal(request, response, chain);
    }

    protected String obtainCmpcode(HttpServletRequest request) {
        return request.getParameter(clientCodeParameter);
    }

    public static final String SPRING_SECURITY_FORM_CLIENTCODE_KEY = "clientcode";

    private String clientCodeParameter = SPRING_SECURITY_FORM_CLIENTCODE_KEY;

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    /**
     * Sets the parameter name which will be used to obtain the clientCode from the
     * login request.
     *
     * @param clientCodeParameter the parameter name. Defaults to "clientCode".
     */
    public void setClientParameter(String clientcode) {
        Assert.hasText(clientCodeParameter, "clientCode parameter must not be empty or null");
        this.clientCodeParameter = clientcode;
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition
     * to the password. A delimiter such as a pipe (|) should be used to separate
     * the password and extended value(s). The <code>AuthenticationDao</code> will
     * need to generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the
     *         <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the
     *         <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    private void setUnauthorizedResponse(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    /**
     * Apply this filter to only requests which are in '/app/**' format
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        return pathInfo == null || !pathInfo.contains("/app/");
    }
}
