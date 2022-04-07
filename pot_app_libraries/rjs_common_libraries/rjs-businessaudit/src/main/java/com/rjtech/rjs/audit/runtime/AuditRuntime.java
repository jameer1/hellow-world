package com.rjtech.rjs.audit.runtime;

class AuditRuntime {
    private AuditRuntime() {

    }

    private static String auditFlag = "true";

    static String getAuditFlag() {
        return AuditRuntime.auditFlag;
    }

    static synchronized void setAuditFlag(String auditFlag) {
        AuditRuntime.auditFlag = auditFlag;
    }

}
