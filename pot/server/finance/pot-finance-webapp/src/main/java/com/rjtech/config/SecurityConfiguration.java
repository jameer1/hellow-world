package com.rjtech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.rjtech.pot.authentication.TokenAuthEntryPoint;

@Configuration
@EnableWebSecurity
@ImportResource({ "classpath:spring-security-core.xml" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("potAuthenticationProcessingFilter")
    GenericFilterBean potAuthenticationProcessingFilter;

    @Autowired
    private TokenAuthEntryPoint tokenAuthEntryPoint;

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

    }

    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {

        public GlobalSecurityConfiguration() {
            super();
        }

    }

}
