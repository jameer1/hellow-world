package com.rjtech.rjs.audit.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rjtech.rjs.audit.dto.BusinessAuditDTO;


public class BusinessAuditSinkConsoleImpl implements BusinessAuditSink {

  
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAuditSinkConsoleImpl.class);


    
    public BusinessAuditDTO getAuditRecord(String serviceId) {
        return null;
    }

  
    public Long saveAudit(BusinessAuditDTO businessAuditDTO) {
        LOGGER.debug("Saved Business Audit for : "
                + businessAuditDTO.getServiceId() + " with id : 1 and status " + businessAuditDTO.getServiceStatus());
        return 1L;
    }

}
