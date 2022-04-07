package com.rjtech.rjs.audit.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;

/**
 * @author Sreenivasa Rao Kollu
 * @description Listener Class to enable or disbale business-audit at runtime.

 */
public class AuditRuntimeListener implements ApplicationListener<HttpSessionCreatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditRuntimeListener.class);

 
    public void onApplicationEvent(HttpSessionCreatedEvent sessionEvent) {
        LOGGER.debug("onApplicationEvent() invoked...");
        HttpSession session = sessionEvent.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        AuditRuntimeStrategy auditRuntimeStrategy=null;
        auditRuntimeStrategy = (AuditRuntimeStrategy) ctx.getBean("auditRuntimeStrategy");
        if(null !=auditRuntimeStrategy) {
            auditRuntimeStrategy.checkAuditRuntimeFlag();
        }   
    }

}
