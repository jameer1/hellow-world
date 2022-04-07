package com.rjtech.rjs.exception.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionLogMethod {
	/**
	 * This parameter depicts the source of the application where this method is
	 * being implemented.
	 * 
	 * @return source
	 */
	String source();

	/**
	 * This parameter depicts the source of the module where this method is
	 * being implemented.
	 * 
	 * @return module
	 */
	String module();

	/**
	 * This parameter depicts the error type such as System => 1 Security => 2
	 * Database => 3 Service/Application => 4 Business => 5
	 * 
	 * @return errorType
	 */

	String errorType();

	/**
	 * Critical Error => 1 Error => 2 Warning => 3 Information/OK => 4 Invalid
	 * request => 5
	 * 
	 * @return severity
	 */

	String severity();

	/**
	 * 
	 * @return errorCode
	 */

	String errorCode() default "0000";

	/**
	 * This parameter depicts whether to audit the arguements values being
	 * passed, this will be in effect only if business audit module is enabled.
	 * Set this value to false,if the parameter values are confidential or else
	 * not required to audit.
	 * 
	 * @return boolean.
	 */
	boolean auditServiceArgumentValues() default true;

	/**
	 * This parameter depicts whether to audit the output parameter values being
	 * returned,this will be in effect only if business audit module is enabled.
	 * Set this value to false,if the return values are confidential or else not
	 * required to audit.
	 * 
	 * @return boolean.
	 */
	boolean auditServiceReturnValues() default true;
	
	String logType();

}
