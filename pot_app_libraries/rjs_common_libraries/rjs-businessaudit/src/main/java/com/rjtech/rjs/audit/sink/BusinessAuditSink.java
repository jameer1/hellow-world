package com.rjtech.rjs.audit.sink;

import com.rjtech.rjs.audit.dto.BusinessAuditDTO;


public interface BusinessAuditSink {
   
    Long saveAudit(BusinessAuditDTO businessAuditDTO);

    BusinessAuditDTO getAuditRecord(String serviceId);

}
