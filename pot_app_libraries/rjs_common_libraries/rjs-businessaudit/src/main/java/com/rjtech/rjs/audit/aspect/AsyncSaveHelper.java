package com.rjtech.rjs.audit.aspect;

import com.rjtech.rjs.audit.dto.BusinessAuditDTO;
import com.rjtech.rjs.audit.sink.BusinessAuditSink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * The Class AsyncSaveHelper. This class is created for invoking the save business audit
 * asynchronously. A separate class is required, as this method is invoked from an aspect
 * proxy and Async requires another proxy.
 *
 * @author Sreenivasa Rao Kollu
 * @version 3.1.0.CR1. 20 Sept 2016
 * @since JDK Version 1.6, Spring Framework Version 4.1.0
 */
@Component
public class AsyncSaveHelper {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AsyncSaveHelper.class);

 
    @Async
    public void processBusinessAuditDTO(BusinessAuditSink businessAuditSink,
                                        BusinessAuditDTO businessAuditDTO) {
        LOGGER.debug("Processing Audit Element Asynchronously");
        businessAuditSink.saveAudit(businessAuditDTO);

    }

}
