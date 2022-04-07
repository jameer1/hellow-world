package com.rjtech.rjs.audit.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * BusinessAuditDTO class. The audit elements are encapsulated in this class.
 * @author Sreenivasa Rao Kollu
 * @version 3.1.0.CR1. 20 Sept 2016
 * @since JDK Version 1.6, Spring Framework Version 4.1.0
 */
public class BusinessAuditDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The Constant SUCCESS.
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * The Constant FAILED.
     */
    public static final String FAILED = "FAILED";

    /**
     * The Constant INFLIGHT.
     */
    public static final String INFLIGHT = "INFLIGHT";


    /**
     * Instantiates a new business audit dto.
     */
    public BusinessAuditDTO() {
    }


    /**
     * Instantiates a new business audit dto.
     *
     * @param otherDTO the other dto
     */
    public BusinessAuditDTO(BusinessAuditDTO otherDTO) {
        this.arguments = otherDTO.getArguments();
        this.endTime = otherDTO.getEndTime();
        this.executionTime = otherDTO.getExecutionTime();
        this.id = otherDTO.getId();
        this.returnValue = otherDTO.getReturnValue();
        this.serviceId = otherDTO.getServiceId();
        this.serviceMethodSignature = otherDTO.getServiceMethodSignature();
        this.serviceName = otherDTO.getServiceName();
        this.startTime = otherDTO.getStartTime();
        this.throwable = otherDTO.getThrowable();
        this.userId = otherDTO.getUserId();
    }


    /**
     * The id.
     */
    private volatile Long id;

    /**
     * The id.
     */
    private volatile Long parentId;

    /**
     * The service id.
     */
    private String serviceId;

    /**
     * The user id.
     */
    private String userId;

    /**
     * The service name.
     */
    private String serviceName;

    /**
     * The service method signature.
     */
    private String serviceMethodSignature;

    /**
     * The execution time.
     */
    private Date executionTime;

    /**
     * The arguments.
     */
    private Object[] arguments;

    /**
     * The return value.
     */
    private Object returnValue;

    /**
     * The throwable.
     */
    private Throwable throwable;

    /**
     * The service status.
     */
    private String serviceStatus;

    /**
     * The start time.
     */
    private Long startTime;

    /**
     * The end time.
     */
    private Long endTime;

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
    public Object[] getArguments() {
        return null != arguments ? arguments.clone() : null;
    }

    /**
     * Sets the arguments.
     *
     * @param arguments the new arguments
     */
    public void setArguments(Object[] arguments) {
        if (null != arguments) {
            this.arguments = Arrays.copyOf(arguments,arguments.length);
        } else {
            this.arguments = null;
        }
    }

    /**
     * Gets the return value.
     *
     * @return the return value
     */
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Sets the return value.
     *
     * @param returnValue the new return value
     */
    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * Gets the throwable.
     *
     * @return the throwable
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * Sets the throwable.
     *
     * @param throwable the new throwable
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
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
     * @return the parent_id
     */
    public Long getParentId() {
        return parentId;
    }


    /**
     * <p>Setter for the field <code>parentId</code>.</p>
     *
     * @param parentId the parent_id to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BusinessAuditDTO [id=" + id + ", parent_id=" + parentId
                + ", serviceId=" + serviceId + ", userId=" + userId
                + ", serviceName=" + serviceName + ", serviceMethodSignature="
                + serviceMethodSignature + ", executionTime=" + executionTime
                + ", arguments=" + Arrays.toString(arguments)
                + ", returnValue=" + returnValue + ", throwable=" + throwable
                + ", serviceStatus=" + serviceStatus + ", startTime="
                + startTime + ", endTime=" + endTime + "]";
    }


}
