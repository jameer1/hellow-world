package com.rjtech.rjs.audit.sink.database;

import com.emirates.egsframework.core.annotations.EGSIDGenerator;
import com.emirates.egsframework.persistence.audit.Auditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The JPA entity class for business audit.
 *
 * @author Sujith P. P. (S337680)
 * @version 3.1.0.CR1. 03 Apr 2012
 * @revision 05 May 2012 / Sajith P. / Integrated with es-persistence
 * @revision 09 May 2012 / Sujith P. P. / Extended Auditable interface
 * @since JDK Version 1.6, Spring Framework Version 3.1.0
 */
@Entity
@Table(name = "BUSINESS_AUDIT")
@EGSIDGenerator
public class BusinessAudit extends Auditable {

    /**
     * The id.
     */
    @Id
    @Column(name = "audit_id")
    private Long id;
    /*parentId*/
    @Column(name = "parent_audit_id")
    private Long parentId;

    /**
     * The service id.
     */
    @NotNull
    @Column(name = "service_id")
    private String serviceId;

    /**
     * The user id.
     */
    @NotNull
    @Column(name = "user_id")
    private String userId;

    /**
     * The service name.
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * The service method signature.
     */
    @NotNull
    @Column(name = "method_signature")
    private String serviceMethodSignature;

    /**
     * The execution time.
     */
    @NotNull
    @Column(name = "execution_time")
    private Date executionTime;

    /**
     * The arguments.
     */
    @Column(name = "arguments")
    private String arguments;

    /**
     * The return value.
     */
    @Column(name = "return_value", columnDefinition = "CLOB")
    @Lob
    private String returnValue;

    /**
     * The service status.
     */
    @Column(name = "service_status")
    private String serviceStatus;

    /**
     * The throwable.
     */
    @Column(name = "exception", columnDefinition = "CLOB")
    @Lob
    private String throwable;

    /**
     * The start time.
     */
    @Column(name = "start_time")
    private Long startTime;

    /**
     * The end time.
     */
    @Column(name = "end_time")
    private Long endTime;
    
    
    /**
     * The no of txns.
     */
    @Transient
    private long numTxns;

    /**
     * The avg response time.
     */
    @Transient
    private double avgResponseTime;
    
    @Column(name = "BSA_ID")
    private long id;
    @Column(name = "BSA_SERVICE_ID")
    private long serviceId;
    @Column(name = "BSA_SERVICE_NAME")
    private String serviceName;
    @Column(name = "BSA_PARENT_ID")
    private long parentId;
    @Column(name = "BSA_USER_ID")
    private long UserId;
    @Column(name = "BSA_CLIENT_ID")
    private long clientId;
    @Column(name = "BSA_METHODE_SIGN")
    private String methodeSing;
    @Column(name = "BSA_EXECUTION_TIME")
    private Date executionTime;
    @Column(name = "BSA_START_TIME")
    private  Date startTime;
    @Column(name = "BSA_FINESH_TIME")
    private  Date fineshTime;
    @Column(name = "BSA_REQ")
    private String req;
    @Column(name = "BSA_RESPONCE")
    private String responce;
    @Column(name = "BSA_EXCEPTION")
    private String exception;
    @Column(name = "BSA_SERVICE_STATUS")
    private String serviceStatus;
    @Column(name = "BSA_REQ_SOURCE")
    private String reqSource;
    @Column(name = "BSA_DEVICE_TYPE")
    private String deviceType;
    
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the service id.
     *
     * @return the service id
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * Sets the service id.
     *
     * @param serviceId the new service id
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the service name.
     *
     * @return the service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the service name.
     *
     * @param serviceName the new service name
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets the service method signature.
     *
     * @return the service method signature
     */
    public String getServiceMethodSignature() {
        return serviceMethodSignature;
    }

    /**
     * Sets the service method signature.
     *
     * @param serviceMethodSignature the new service method signature
     */
    public void setServiceMethodSignature(String serviceMethodSignature) {
        this.serviceMethodSignature = serviceMethodSignature;
    }

    /**
     * Gets the execution time.
     *
     * @return the execution time
     */
    public Date getExecutionTime() {
        return null != executionTime ? new Date(executionTime.getTime()) : null;
    }

    /**
     * Sets the execution time.
     *
     * @param executionTime the new execution time
     */
    public void setExecutionTime(Date executionTime) {
        this.executionTime = null != executionTime ? new Date(executionTime.getTime()) : null;
    }

    /**
     * Gets the arguments.
     *
     * @return the arguments
     */
    public String getArguments() {
        return arguments;
    }

    /**
     * Sets the arguments.
     *
     * @param arguments the new arguments
     */
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Gets the return value.
     *
     * @return the return value
     */
    public String getReturnValue() {
        return returnValue;
    }

    /**
     * Sets the return value.
     *
     * @param returnValue the new return value
     */
    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * Gets the service status.
     *
     * @return the service status
     */
    public String getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Sets the service status.
     *
     * @param serviceStatus the new service status
     */
    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    /**
     * Gets the throwable.
     *
     * @return the throwable
     */
    public String getThrowable() {
        return throwable;
    }

    /**
     * Sets the throwable.
     *
     * @param throwable the new throwable
     */
    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the new start time
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the new end time
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * <p>Getter for the field <code>parentId</code>.</p>
     *
     * @return the parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * <p>Setter for the field <code>parentId</code>.</p>
     *
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    

    public long getNumTxns() {
		return numTxns;
	}

	public void setNumTxns(long numTxns) {
		this.numTxns = numTxns;
	}

	public double getAvgResponseTime() {
		return avgResponseTime;
	}

	public void setAvgResponseTime(double avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public BusinessAudit() {
    	
    }
    
    public BusinessAudit(String serviceName, long numTxns, double avgResponseTime) {
    	this.serviceName = serviceName;
    	this.numTxns = numTxns;
    	this.avgResponseTime = avgResponseTime;
    }

}
