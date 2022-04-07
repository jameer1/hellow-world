package com.rjtech.rjs.audit.runtime;


/**
 * @author Sreenivasa Rao Kollu
 * @description This interface has to be implemented for injecting beans having cyclic dependencies.
 * @see AuditRuntimeStrategy
 *
 */
public interface AuditRuntimeRepo {

     String getRuntimeAuditFlagDB();

}
