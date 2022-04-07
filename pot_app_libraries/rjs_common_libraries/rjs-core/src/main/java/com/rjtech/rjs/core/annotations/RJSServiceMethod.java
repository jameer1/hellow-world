package com.rjtech.rjs.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for identifying that the target method is an
 * RJService Method. Any method in a service class which implements a service
 * should be annotated with this annotation to leverage the functionality
 * provided by the reusable components as part of the RJSpring+ framework.
 * Examples include Business Audit, Logging, advanced Persistence features etc.
 * <p/>
 * The serviceId specifies the service method id service method being
 * implemented.
 *
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RJSServiceMethod {

    /**
     * This parameter depicts the service id of the method being implemented.
     *
     * @return service id.
     */
    String serviceId();

    boolean txXAEnabled() default false;

    /**
     * This parameter depicts whether to audit the arguements values being passed,
     * this will be in effect only if business audit module is enabled. Set this
     * value to false,if the parameter values are confidential or else not required
     * to audit.
     *
     * @return boolean.
     */
    boolean auditServiceArgumentValues() default true;

    /**
     * This parameter depicts whether to audit the output parameter values being
     * returned,this will be in effect only if business audit module is enabled. Set
     * this value to false,if the return values are confidential or else not
     * required to audit.
     *
     * @return boolean.
     */
    boolean auditServiceReturnValues() default true;

}
