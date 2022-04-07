package com.rjtech.rjs.audit.sink.database.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.rjtech.rjs.audit.sink.database.BusinessAudit;

import javax.xml.bind.Marshaller;
import java.util.Date;


public class BusinessAuditBuilder {


    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAuditBuilder.class);

    
    @Qualifier("xstreamMarshaller")
    @Autowired
    private Marshaller marshaller;

 
    private BusinessAudit businessAudit;

   
    public BusinessAuditBuilder() {
        businessAudit = new BusinessAudit();
    }

   
    public BusinessAuditBuilder serviceId(String serviceId) {

        businessAudit.setServiceId(serviceId);
        return this;
    }

 
    public BusinessAuditBuilder userId(String userId) {
        businessAudit.setUserId(userId);
        return this;
    }

  
    public BusinessAuditBuilder serviceName(String serviceName) {
        businessAudit.setServiceName(serviceName);
        return this;
    }

  
    public BusinessAuditBuilder serviceMethodSignature(
            String serviceMethodSignature) {
        businessAudit.setServiceMethodSignature(serviceMethodSignature);
        return this;
    }

  
    public BusinessAuditBuilder executionTime(Date executionTime) {
        businessAudit.setExecutionTime(executionTime);
        return this;
    }

  
    public BusinessAuditBuilder arguments(String arguments) {
        businessAudit.setArguments(arguments);
        return this;
    }

 
    public BusinessAuditBuilder returnValue(String returnValue) {
        businessAudit.setReturnValue(returnValue);
        return this;
    }


    public BusinessAuditBuilder throwable(String throwable) {
        businessAudit.setThrowable(throwable);
        return this;
    }

    
    public BusinessAuditBuilder serviceStatus(String serviceStatus) {
        businessAudit.setServiceStatus(serviceStatus);
        return this;
    }

  
    public BusinessAuditBuilder id(Long id) {
        businessAudit.setId(id);
        return this;
    }

 
    public BusinessAuditBuilder parentId(Long id) {
        businessAudit.setParentId(id);
        return this;
    }

  
    public BusinessAudit build() {
        return businessAudit;
    }

    
    public BusinessAuditBuilder startTime(Long startTime) {
        LOGGER.debug("******** Start Time " + startTime);
        businessAudit.setStartTime(startTime);
        return this;
    }

 
    public BusinessAuditBuilder endTime(Long endTime) {
        businessAudit.setEndTime(endTime);
        return this;
    }

}
