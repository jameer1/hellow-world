package com.rjtech.pot.authentication;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.mw.dto.login.LoginRequest;
import com.rjtech.mw.dto.login.LoginResponse;
import com.rjtech.mw.service.login.LoginService;
import com.rjtech.pot.authentication.exceptions.POTTokenExpiredException;
import com.rjtech.rjs.appuser.utils.AppUserDetails;
import com.rjtech.rjs.core.exception.LienceExpiredException;
import com.rjtech.rjs.service.token.RJSTokenService;

public class POTAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(POTAuthenticationProvider.class);

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("rjsTokenService")
    private RJSTokenService rjsTokenService;

    @Autowired
    @Qualifier("loginService")
    private LoginService loginService;

    public POTAuthenticationProvider() {

    }

    public Authentication authenticate(Authentication authentication) {
        POTAuthenticationToken auth = (POTAuthenticationToken) authentication;
        if (CommonUtil.objectNotNull(auth.getToken())
                && (CommonUtil.objectNullCheck(auth.getPrincipal()) || CommonUtil.objectNullCheck(auth.getCredentials())
                        || CommonUtil.objectNullCheck(auth.getClientcode()))) {
            String token = (String) auth.getToken();
            AppUserDetails appUserDetails = rjsTokenService.fetchUserByToken(token);
            if (appUserDetails == null)
                throw new POTTokenExpiredException("Token expired. Please Login again");
            return new POTAuthenticationToken(appUserDetails, null, null, token, appUserDetails.getAuthorities());
        } else {
            String username = (String) auth.getPrincipal();
            String password = (String) auth.getCredentials();
            String clientcode = (String) auth.getClientcode();
            String token = null;

            LoginRequest loginRequest = new LoginRequest(username, password, clientcode);

            LoginResponse loginResponse = null;
            if (CommonUtil.isNotBlankStr(clientcode)) {
                loginResponse = loginService.validateExternalLogin(loginRequest);
            } else {
                loginResponse = loginService.validateInternalLogin(loginRequest);
            }
            Date date = new Date();
            log.info("Login Response *******  {}", loginResponse);
            log.info("Login Response *******  {}  -  {}  - {}  ", loginResponse.getUserId().longValue(),
                    loginResponse.getUserId(), loginResponse.getLicence());
            if (loginResponse != null && loginResponse.getUserId().longValue() > 0 && (loginResponse.getUserId() == 1
                    || loginResponse.getLicence() != null && loginResponse.getLicence().after(date))) {

                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(loginResponse.getUserId()));
                sb.append("#");
                sb.append(String.valueOf(loginResponse.getUserType()));

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(sb.toString());
                AppUserDetails appUserDetails = (AppUserDetails) userDetails;
                appUserDetails.setClientId(loginResponse.getClientId());
                appUserDetails.setClientCode(loginResponse.getClientCode());
                appUserDetails.setAdminClientId(loginResponse.getAdminClientId());
                appUserDetails.setDesignation(loginResponse.getDesignation());

                String repotoken = rjsTokenService.findTokenByUserId(appUserDetails.getUserId());
                if (CommonUtil.isNotBlankStr(repotoken)) {
                    rjsTokenService.delete(repotoken);
                }

                token = UUID.randomUUID().toString();
                appUserDetails.setToken(token);
                rjsTokenService.add(appUserDetails, token);
                log.info("  Token value for loggedin user " + token);

                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            } else {

                if (loginResponse != null && loginResponse.getLicence() != null
                        && loginResponse.getLicence().before(date)) {
                    //
                    throw new LienceExpiredException("Your Licence is Expired");

                }
                throw new UsernameNotFoundException("Invalid credentials. Try again");

            }

        }
    }

    public boolean supports(Class<?> authentication) {
        return POTAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void afterPropertiesSet() throws Exception {
        if (loginService == null || rjsTokenService == null || userDetailsService == null) {
            throw new SecurityException("property loginService/rjsTokenService/userDetailsServiceis null");
        }

    }

}