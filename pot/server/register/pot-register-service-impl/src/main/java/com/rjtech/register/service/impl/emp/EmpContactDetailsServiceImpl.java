package com.rjtech.register.service.impl.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.emp.dto.EmpContactDtlTO;
import com.rjtech.register.emp.model.EmpContactDtlEntity;
import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;
import com.rjtech.register.repository.emp.EmpContactDtlRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpContactDetailsService;
import com.rjtech.register.service.handler.emp.EmpContactDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "empContactDetailsService")
@RJSService(modulecode = "empContactDetailsService")
@Transactional
public class EmpContactDetailsServiceImpl implements EmpContactDetailsService {

    @Autowired
    private EmpContactDtlRepository empContactDtlRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    public EmpContactDetailsResp getEmpContactDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpContactDetailsResp empContactDetailsResp = new EmpContactDetailsResp();

        EmpContactDtlEntity empContactDtlEntity = empContactDtlRepository
                .findEmpContacts(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        if (CommonUtil.objectNotNull(empContactDtlEntity)) {
            EmpContactDtlTO contactDtl = EmpContactDtlHandler.convertEntityToPOJO(empContactDtlEntity);
            empContactDetailsResp.getEmpContactDtlTOs().add(contactDtl);
        }

        return empContactDetailsResp;
    }

    public void saveEmpContactDetails(EmpContactSaveReeq empContactSaveReq) {
        List<EmpContactDtlEntity> contactDtlEntities = EmpContactDtlHandler
                .convertPOJOsToEntities(empContactSaveReq.getEmpContactDtlTOs(), empRegisterRepository);
        empContactDtlRepository.save(contactDtlEntities);
    }

    public void deactivateEmpContactDetails(EmpContactsDeactiveReq empContactsDeactiveReq) {
        empContactDtlRepository.deactivateEmpContact(empContactsDeactiveReq.getEmpContactIds(),
                empContactsDeactiveReq.getStatus());
    }

}
