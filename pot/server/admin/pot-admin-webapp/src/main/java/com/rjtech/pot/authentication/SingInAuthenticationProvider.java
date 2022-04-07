package com.rjtech.pot.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.rjtech.rjs.appuser.utils.AppUserDetails;
import com.rjtech.rjs.service.token.RJSTokenService;

public class SingInAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingInAuthenticationProvider.class);

    @Autowired
    @Qualifier("rjsTokenService")
    private RJSTokenService rjsTokenService;

    public void afterPropertiesSet() throws Exception {
        if (rjsTokenService == null) {
            throw new SecurityException("property tokenProviderService is null");
        }

    }

    public Authentication authenticate(Authentication authentication) {
        SignInAuthenticationToken auth = (SignInAuthenticationToken) authentication;
        AppUserDetails appUserDetails = rjsTokenService.fetchUserByToken(auth.getPottoken());
        return new UsernamePasswordAuthenticationToken(appUserDetails, null, appUserDetails.getAuthorities());
    }

    public boolean supports(Class<?> authentication) {
        return SignInAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public RJSTokenService getRjsTokenService() {
        return rjsTokenService;
    }

    public void setRjsTokenService(RJSTokenService rjsTokenService) {
        this.rjsTokenService = rjsTokenService;
    }

}
