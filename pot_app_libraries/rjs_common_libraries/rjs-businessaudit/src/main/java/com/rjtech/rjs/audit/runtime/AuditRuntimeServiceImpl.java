package com.rjtech.rjs.audit.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Sreenivasa Rao Kollu
 * @description Service Impl of Runtime Business Audit.

 */

@Service
public class AuditRuntimeServiceImpl implements AuditRuntimeService {
    private static final Logger LOG = LoggerFactory.getLogger(AuditRuntimeServiceImpl.class);

    @Autowired
    private AuditRuntimeRepo auditRuntimeRepoImpl;


    public String getRuntimeAuditFlagDB() {
        String auditFlagDB = "true";
        try {
            auditFlagDB = auditRuntimeRepoImpl.getRuntimeAuditFlagDB();
        } catch (Exception e) {
            LOG.error("Exception in getRuntimeAuditFlagDB() : " + e.getMessage());
        }
        return auditFlagDB;
    }

}
