/* Copyright 2012-2015 Emirates Group */

package com.rjtech.rjs.audit.aspect;

import com.emirates.egsframework.core.annotations.EGSServiceMethod;
import com.emirates.egsframework.security.context.EGSSecurityContext;
import com.emirates.egsframework.transaction.TransactionIDHolder;
import com.rjtech.rjs.audit.dto.BusinessAuditDTO;
import com.rjtech.rjs.audit.runtime.AuditRuntimeStrategy;
import com.rjtech.rjs.audit.sink.BusinessAuditSink;
import com.rjtech.rjs.audit.util.XMLUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This Aspect class is used for intercepting the service methods from the
 * application and performing audit of those service methods. The requirement
 * for this aspect is that <br>
 * <ul>
 * <li>the service call should be annotated with the
 * <code>EGServiceMethod</code> annotation.</b>
 * </ul>
 * <p/>
 * All such methods are handled by the interceptor and the following details are
 * audited and passed on to an implementation of the BusinessAuditSink for
 * further processing.
 * <ul>
 * <li><b>Service Id</b> - derived from the <code>EGServiceMethod</code>
 * annotation field</li>
 * <li><b>Service Name</b> - the service method name</li>
 * <li><b>Service Method Signature</b></li>
 * <li><b>User Id</b> - the logged in user id invoking the service method</li>
 * <li><b>Execution Time</b> - the time when this service is executed</li>
 * <li><b>Service Status</b> - the current status of this service - INFLIGHT or
 * SUCCESS or FAILED</li>
 * <li><b>Arguments</b> - the arguments passed in to the service method as XML.
 * It is possible to configure if this is required or not by setting the
 * <code>auditArgumentValues</code> property in the bean configuration for
 * <code>auditor</code>. For more details see bean config details below.</li>
 * <li><b>Return Values</b> - the return values of the service method as XML. It
 * is possible to configure if this is required or not by setting the
 * <code>auditReturnValues</code> property in the bean configuration for
 * <code>auditor</code>. For more details see bean config details below.</li>
 * <li><b>Throwable</b> - the exception stack trace thrown by the service
 * method, if any.</li>
 * <li><b>Start Time</b> - the start time of the service method</li>
 * <li><b>End Time</b> - the end time of this service method.</li>
 * </ul>
 * <br />
 * The audit record details are passed on to an implementation of the
 * <code>BusinessAuditSink</code> interface. Example sink implementations
 * provided out of the box are a database based implementation and a JMS based
 * implementation.
 * <br>
 *
 * @author Sujith P. P. (S337680)
 * @revision 3.2.0 by Shameer Thaha (s779372) in May 2013/ Logic added to handle runtime business audit.
 * @since 3.1.0
 */
@Component
public class BusinessAuditAspect implements Ordered {

    /*
     * logger instance
     */
    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAuditAspect.class);

    /*
     * the implementation of this interface will contain the logic of what to
     * do with the audit record. it can be a database implementation where the
     * audit is stored in a database, or it can be a jms based implementation
     * where the data is put in a queue.
     */

    private BusinessAuditSink businessAuditSink;

    /*
     * flag to indicate if the return values from a business method needs to
     * be part of the audit record
     */

    private boolean auditReturnValues;

    /*
     * Flag to indicate if the argument values in a business method needs to
     * be part of the audit record
     */

    private boolean auditArgumentValues;


    /*
     * flag to indicate if exception details and input arguments need
     * to be logged to console
     */

    private boolean logExceptionDetailsToConosle;

    @Autowired
    private XMLUtil xmlUtil;

    private int order;

    private boolean asynchronous;

    @Autowired
    private AsyncSaveHelper asyncSaveHelper;


    @Autowired
    AuditRuntimeStrategy auditRuntimeStrategy;


    /**
     * This method intercepts all the public methods which have the annotation
     * <code>@EGServiceMethod.</code>.
     * Invoke business audit if runtime audit flag is true.
     *
     * @param joinPoint     - the callback
     * @param serviceMethod - annotation specifying that the method is a service method
     * @return the result of the service method invocation
     * @throws java.lang.Throwable - any exception thrown by the service method
     * @see AuditRuntimeStrategy
     */
    public Object auditServiceCall(ProceedingJoinPoint joinPoint, EGSServiceMethod serviceMethod) throws Throwable {
        LOGGER.debug("auditServiceCall():auditRuntimeStrategy.getAuditRuntimeFlag() : " + auditRuntimeStrategy.getAuditRuntimeFlag());

        if ("true".equalsIgnoreCase(auditRuntimeStrategy.getAuditRuntimeFlag())) {
            LOGGER.debug("auditServiceCall(): Business Audit enabled...");
            return doAudit(joinPoint, serviceMethod);
        } else {
            LOGGER.debug("auditServiceCall(): Business Audit disabled...");
            return joinPoint.proceed();
        }
    }


    /**
     * This method intercepts all the public methods which have the annotation
     * <code>@EGServiceMethod.</code> The details of this method including the
     * arguments, return values, serviceId, exceptions, if any, are persisted in
     * the database in a BUSINESS_AUDIT table.
     *
     * @param joinPoint     - the callback
     * @param serviceMethod - annotation specifying that the method is a service method
     * @return the result of the service method invocation
     * @throws Throwable - any exception thrown by the service method
     */

    private Object doAudit(ProceedingJoinPoint joinPoint, EGSServiceMethod serviceMethod) throws Throwable {
        // build the Business Audit DTO. The service status at this point will
        // be INFLIGHT.

        BusinessAuditDTO initialBusinessAuditDTO = buildBusinessAuditDTO(joinPoint, serviceMethod);

        // Creating a new finalBusinessAuditDTO as a copy of the initialDTO. This
        // is required for the async process to complete. if the dto is not copied,
        // before the first insert happens, the main thread would have updated the
        // status of the service from inflight to success/failed. So the inflight
        // record would not be created and instead, there are chances for two records
        // with the final status to be created depending on the speed of execution of the
        // main business method.
        BusinessAuditDTO finalBusinessAuditDTO = new BusinessAuditDTO(initialBusinessAuditDTO);
        // save the business audit data prior to executing the service method
        Long auditId = null;
        auditId = processBusinessAuditDTO(businessAuditSink, initialBusinessAuditDTO);
        TransactionIDHolder.setTransactionId(auditId);
        finalBusinessAuditDTO.setParentId(TransactionIDHolder.getParentTransactionId());
        finalBusinessAuditDTO.setId(auditId);

        Object result;
        try {
            // execute the business method
            result = joinPoint.proceed();

            // after execution, fill up the return values in the audit dto if
            // marked as true in the audit config
            if (auditReturnValues && serviceMethod.auditServiceReturnValues()) {
                finalBusinessAuditDTO.setReturnValue(result);
            }

            // change the service status to SUCCESS
            finalBusinessAuditDTO.setServiceStatus(BusinessAuditDTO.SUCCESS);

        } catch (Throwable e) {

            if (logExceptionDetailsToConosle) {
                logExceptionDetailsToConsole(joinPoint, e);
            }

            // In case an exception was thrown by the service method,
            // set the exception in the audit dto and the service status as
            // failed and rethrow the exception
            finalBusinessAuditDTO.setThrowable(e);
            finalBusinessAuditDTO.setServiceStatus(BusinessAuditDTO.FAILED);
            throw e;

        } finally {
            // finally, set the end time of the service and modify the existing
            // audit record with the new values.
            if (TransactionIDHolder.isParent()) {
                TransactionIDHolder.clearAll();
            } else {
                TransactionIDHolder.clearTransactionId();
            }
            finalBusinessAuditDTO.setEndTime(System.currentTimeMillis());
            processBusinessAuditDTO(businessAuditSink, finalBusinessAuditDTO);
        }
        return result;

    }

    private void logExceptionDetailsToConsole(ProceedingJoinPoint joinPoint,
                                              Throwable throwable) {

        String targetClassName = null;
        String targetMethodName = null;
        Object[] inputArguments = null;
        try {
            targetClassName = joinPoint.getTarget().getClass().getName();
            targetMethodName = joinPoint.getSignature().getName();
            inputArguments = joinPoint.getArgs();

            LOGGER.error("{}:{} \n INPUT OBJECT \n {} \n EXCEPTION DETAILS \n {} \n ", new Object[]{targetClassName, targetMethodName,
                    xmlUtil.getXMLString(inputArguments), xmlUtil.getXMLString(throwable)});

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private Long processBusinessAuditDTO(BusinessAuditSink businessAuditSink, BusinessAuditDTO businessAuditDTO) {

        Long auditId = null;

        try {
            if (isAsynchronous()) {
                asyncSaveHelper.processBusinessAuditDTO(businessAuditSink, businessAuditDTO);
            } else {
                auditId = businessAuditSink.saveAudit(businessAuditDTO);
            }

            LOGGER.debug("Business Audit DTO id " + businessAuditDTO.getId() + " and status " + businessAuditDTO.getServiceStatus());
            LOGGER.info("Audit element submitted for Sink Update");
        } catch (Exception e) {
            LOGGER.error("Audit specific exception occurred. Aborting Business Audit Sink Update");
        }

        return auditId;
    }


    /**
     * Utility method for building the BusinessAuditDTO
     *
     * @param joinPoint the join point
     * @param egService the eg service
     * @return the business audit dto
     */
    private BusinessAuditDTO buildBusinessAuditDTO(
            ProceedingJoinPoint joinPoint, EGSServiceMethod egService) {


        BusinessAuditDTO businessAuditDTO = new BusinessAuditDTO();
        businessAuditDTO.setServiceId(egService.serviceId());
        businessAuditDTO.setServiceMethodSignature(joinPoint.getSignature()
                .toLongString());
        businessAuditDTO.setServiceName(joinPoint.getSignature().getName());
        businessAuditDTO.setUserId(getLoggedInUser());
        businessAuditDTO.setExecutionTime(new Date());
        businessAuditDTO.setStartTime(System.currentTimeMillis());
        businessAuditDTO.setEndTime(System.currentTimeMillis());
        businessAuditDTO.setServiceStatus(BusinessAuditDTO.INFLIGHT);


        if (auditArgumentValues && egService.auditServiceArgumentValues()) {
            businessAuditDTO.setArguments(joinPoint.getArgs());
        }
        return businessAuditDTO;
    }


    /**
     * Utility method for getting the logged in user.
     *
     * @return the logged in user
     */
    private String getLoggedInUser() {
        return (null != EGSSecurityContext.getCurrentUserName()) ? EGSSecurityContext.getCurrentUserName() : "UNKNOWN_USER";
    }


    /**
     * Getter for auditReturnValues
     *
     * @return auditReturnValues
     */
    public boolean isAuditReturnValues() {
        return auditReturnValues;
    }

    /**
     * Setter for auditReturnValues
     *
     * @param auditReturnValues a boolean.
     */
    public void setAuditReturnValues(boolean auditReturnValues) {
        this.auditReturnValues = auditReturnValues;
    }

    /**
     * Getter for auditArgumentValues
     *
     * @return auditArgumentValues
     */
    public boolean isAuditArgumentValues() {
        return auditArgumentValues;
    }

    /**
     * Setter for auditArgumentValues
     *
     * @param auditArgumentValues a boolean.
     */
    public void setAuditArgumentValues(boolean auditArgumentValues) {
        this.auditArgumentValues = auditArgumentValues;
    }

    /**
     * Getter for logExceptionDetailsToConosle
     *
     * @return a boolean.
     */
    public boolean isLogExceptionDetailsToConosle() {
        return logExceptionDetailsToConosle;
    }


    /**
     * Setter for logExceptionDetailsToConosle
     *
     * @param logExceptionDetailsToConosle a boolean.
     */
    public void setLogExceptionDetailsToConosle(boolean logExceptionDetailsToConosle) {
        this.logExceptionDetailsToConosle = logExceptionDetailsToConosle;
    }

    /**
     * Getter for businessAuditSink
     *
     * @return businessAuditSink
     */
    public BusinessAuditSink getBusinessAuditSink() {
        return businessAuditSink;
    }

    /**
     * Setter for businessAuditSink
     *
     * @param businessAuditSink a {@link com.rjtech.rjs.audit.sink.BusinessAuditSink} object.
     */
    public void setBusinessAuditSink(BusinessAuditSink businessAuditSink) {
        this.businessAuditSink = businessAuditSink;
    }


    /**
     * <p>Setter for the field <code>order</code>.</p>
     *
     * @param order a int.
     */
    public void setOrder(int order) {
        this.order = order;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrder() {
        return order;
    }


    /**
     * <p>isAsynchronous.</p>
     *
     * @return a boolean.
     */
    public boolean isAsynchronous() {
        return asynchronous;
    }


    /**
     * <p>Setter for the field <code>asynchronous</code>.</p>
     *
     * @param asynchronous a boolean.
     */
    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

}
