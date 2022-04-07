package com.rjtech.rjs.audit.sink.database;

import com.rjtech.rjs.audit.dto.BusinessAuditDTO;


/**
 * The Interface BusinessAuditRepository.
 *
 * @author Sujith P. P. (S337680)
 * @version 3.1.0-CR1. 03 Apr 2012
 * @revision 05 May 2012 / Sajith P. / Integrated with es-persistence
 * @since JDK Version 1.6, Spring Framework Version 3.1.0
 */
public interface BusinessAuditRepository {

    /**
     * Save business audit.
     *
     * @param businessAuditDTO the business audit dto
     * @return the saved business audit id, if any depending on the repository implementation
     */
    Object saveBusinessAudit(BusinessAuditDTO businessAuditDTO);

    /**
     * Gets the business audit. This method is not mandatory to be implemented.
     *
     * @param serviceid the serviceid
     * @return the businessAuditDTO.
     */
    BusinessAuditDTO getBusinessAudit(String serviceid);

}
