package com.rjtech.rjs.audit.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rjtech.rjs.core.exception.RJSRuntimeException;

/**
 * @author Sreenivasa Rao Kollu
 * @description Class to handle business audit runtime flag.
 * @revision System parameter is queried via AuditRuntimeService, instead of generic service in order to avoid cyclic dependency.
 * 
 */

@Component
public class AuditRuntimeStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(AuditRuntimeStrategy.class);

    @Autowired
    AuditRuntimeService auditRuntimeServiceImpl;

    void checkAuditRuntimeFlag() {
        try {           
            String auditFlagDB = auditRuntimeServiceImpl.getRuntimeAuditFlagDB();
            LOG.debug("checkAuditRuntimeFlag():AuditRuntime.getAuditFlag() : " + AuditRuntime.getAuditFlag());    
            if (!AuditRuntime.getAuditFlag().equalsIgnoreCase(auditFlagDB)) {
                AuditRuntime.setAuditFlag(auditFlagDB);
                LOG.debug("checkAuditRuntimeFlag(): Audit flag is different in DB, JVM audit flag updated with DB Flag....: ");
            }

        } catch (Exception e) {
            LOG.error("Exception: checkAuditRuntimeFlag() : " + e.getMessage());
            throw new RJSRuntimeException("Audit Runtime Error.", e);
        }
    }
    public String getAuditRuntimeFlag() {
        return AuditRuntime.getAuditFlag();
    }

}
