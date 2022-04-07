package com.rjtech.common.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextProvider.class);

    private static ApplicationContext ctx;

    public synchronized void setApplicationContext(ApplicationContext context) {
        log.info("Setting ApplicationContext through ApplicationContextAware");
        ctx = context;
    }

    public static synchronized ApplicationContext getApplicationContext() {
        return ctx;
    }
}