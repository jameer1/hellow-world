package com.rjtech.rjs.audit.sink.database;

import com.emirates.egsframework.core.annotations.EGSService;
import com.rjtech.rjs.audit.dto.BusinessAuditDTO;
import com.rjtech.rjs.audit.sink.BusinessAuditSink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * The database based implementation for the <code>BusinessAuditSink</code>
 *
 * @author Sujith P. P. (S337680)
 * @version 3.1.0-CR1. 03 Apr 2012
 * @revision 05 May 2012 / Sajith P. / Integrated with es-persistence
 * @since JDK Version 1.6, Spring Framework Version 3.1.0
 */
@EGSService(modulecode = "audit")
public class BusinessAuditSinkDBImpl implements BusinessAuditSink {

    /**
     * The Constant logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(BusinessAuditSinkDBImpl.class);

    /**
     * The business audit repository.
     */
    @Autowired
    private BusinessAuditRepository businessAuditRepository;


    /* (non-Javadoc)
     * @see com.emirates.egsframework.audit.sink.BusinessAuditSink#saveAudit(com.emirates.egsframework.audit.dto.BusinessAuditDTO)
     */

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long saveAudit(BusinessAuditDTO businessAuditDTO) {

        Long savedAuditId = null;
        try {
            businessAuditRepository.saveBusinessAudit(businessAuditDTO);
            savedAuditId = businessAuditDTO.getId();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Saved Business Audit for : "
                + businessAuditDTO.getServiceId() + " with id : "
                + savedAuditId + " and status " + businessAuditDTO.getServiceStatus());
        return savedAuditId;

    }

    /* (non-Javadoc)
     * @see com.emirates.egsframework.audit.sink.BusinessAuditSink#getAuditRecord(java.lang.String)
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public BusinessAuditDTO getAuditRecord(String serviceId) {
        return businessAuditRepository.getBusinessAudit(serviceId);
    }


}
