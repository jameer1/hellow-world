package com.rjtech.rjs.audit.sink.database.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rjtech.rjs.audit.dto.BusinessAuditDTO;
import com.rjtech.rjs.audit.sink.database.BusinessAudit;
import com.rjtech.rjs.audit.sink.database.BusinessAuditRepository;
import com.rjtech.rjs.audit.util.XMLUtil;
import com.rjtech.rjs.persistence.repository.AbstractJPARepository;


@Repository
public class BusinessAuditJPARepository extends  AbstractJPARepository implements     BusinessAuditRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAuditJPARepository.class);


    @Autowired
    private XMLUtil xmlUtil;

  

    public Object saveBusinessAudit(BusinessAuditDTO businessAuditDTO) {
        LOGGER.debug("Business Audit DTO id " + businessAuditDTO.getId()+ " and status " + businessAuditDTO.getServiceStatus());
        BusinessAudit businessAudit = new BusinessAuditBuilder()
                .id(businessAuditDTO.getId())
                .parentId(businessAuditDTO.getParentId())
                .serviceId(businessAuditDTO.getServiceId())
                .arguments(xmlUtil.getXMLString(businessAuditDTO.getArguments()))
                .executionTime(businessAuditDTO.getExecutionTime())
                .returnValue(xmlUtil.getXMLString(businessAuditDTO.getReturnValue()))
                .serviceMethodSignature(
                        businessAuditDTO.getServiceMethodSignature())
                .serviceName(businessAuditDTO.getServiceName())
                .serviceStatus(businessAuditDTO.getServiceStatus())
                .throwable(xmlUtil.getXMLString(businessAuditDTO.getThrowable()))
                .userId(businessAuditDTO.getUserId())
                .startTime(businessAuditDTO.getStartTime())
                .endTime(businessAuditDTO.getEndTime()).build();

        BusinessAudit savedBusinessAudit = (BusinessAudit) makePersistent(businessAudit);
        businessAuditDTO.setId(savedBusinessAudit.getId());
        return this;
    }



    public BusinessAuditDTO getBusinessAudit(String serviceid) {

        @SuppressWarnings("rawtypes")
        List list = getEntityManager()
                .createQuery(
                        "select businessAudit from BusinessAudit businessAudit where businessAudit.serviceId = :serviceid")
                .setParameter("serviceid", serviceid).getResultList();
        BusinessAudit businessAudit = (BusinessAudit) list.get(0);
        return getBusinessAuditDTO(businessAudit);
    }

  
    private BusinessAuditDTO getBusinessAuditDTO(BusinessAudit businessAudit) {

        BusinessAuditDTO businessAuditDTO = new BusinessAuditDTO();

        businessAuditDTO.setEndTime(businessAudit.getEndTime());
        businessAuditDTO.setStartTime(businessAudit.getStartTime());
        businessAuditDTO.setExecutionTime(businessAudit.getExecutionTime());
        businessAuditDTO.setId(businessAudit.getId());
        businessAuditDTO.setServiceId(businessAudit.getServiceId());
        businessAuditDTO.setServiceMethodSignature(businessAudit
                .getServiceMethodSignature());
        businessAuditDTO.setServiceName(businessAudit.getServiceName());
        businessAuditDTO.setServiceStatus(businessAudit.getServiceStatus());
        businessAuditDTO.setUserId(businessAudit.getUserId());
        return businessAuditDTO;

    }


}
