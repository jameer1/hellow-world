package com.rjtech.config;

import java.util.Arrays;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rjtech.centrallib.web.filters.CrossFilter;

@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    @Autowired
    private Environment env;

    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
                DispatcherType.ASYNC);

        if (env.acceptsProfiles(APPConstants.SPRING_PROFILE_PRODUCTION)) {
        }
        initCorsFilter(servletContext, disps);
        log.info("Web application fully configured");
    }

    private void initCorsFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        log.debug("Initializing CROS Filter");
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("crossFilter", new CrossFilter());
        corsFilter.addMappingForUrlPatterns(null, false, "/app/*");
        corsFilter.setAsyncSupported(true);

    }

    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("html", "text/html;charset=utf-8");
        mappings.add("json", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
    }

}
