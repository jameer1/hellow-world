package com.rjtech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.rjtech.pot.authentication.TokenAuthEntryPoint;
import com.rjtech.pot.authentication.handlers.CustomLogoutHandler;

@Configuration
@EnableWebSecurity
@ImportResource({ "classpath:spring-security-core.xml" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("potAuthenticationProcessingFilter")
    GenericFilterBean potAuthenticationProcessingFilter;

    @Autowired
    private TokenAuthEntryPoint tokenAuthEntryPoint;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(false);
        web.ignoring().antMatchers("/bower_components/**").antMatchers("/fonts/**").antMatchers("/images/**")
                .antMatchers("/scripts/**").antMatchers("/styles/**").antMatchers("/views/**").antMatchers("/i18n/**")
                .antMatchers("/swagger-ui/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(tokenAuthEntryPoint).and().csrf().disable()
                .authorizeRequests().antMatchers("/app/**").permitAll();

        http.addFilterAfter(potAuthenticationProcessingFilter, BasicAuthenticationFilter.class);

        // enable session management
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .enableSessionUrlRewriting(false);

        // configure logout
        /*http.logout().clearAuthentication(true).deleteCookies("XSRF-TOKEN", "JSESSIONID").invalidateHttpSession(true)
        	.logoutSuccessUrl("/login");*/
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/app/logout", "POST")).invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessUrl("/").logoutSuccessHandler(customLogoutHandler);

    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {

        public GlobalSecurityConfiguration() {
            super();
        }

    }

}
