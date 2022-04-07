package com.rjtech.register.service.impl.emp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.register.emp.model.EmpBankAccountDtlEntity;
import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;
import com.rjtech.register.repository.emp.EmpBankAccountDtlRepository;
import com.rjtech.register.repository.emp.EmpRegisterRepository;
import com.rjtech.register.service.emp.EmpBankAccountDetailsService;
import com.rjtech.register.service.handler.emp.EmpBankAccountDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "empBankAccountDetailsService")
@RJSService(modulecode = "empBankAccountDetailsService")
@Transactional
public class EmpBankAccountDetailsServiceImpl implements EmpBankAccountDetailsService {

    @Autowired
    private EmpBankAccountDtlRepository empBankAccountDtlRepository;

    @Autowired
    private EmpRegisterRepository empRegisterRepository;

    public EmpBankAccountDtlResp getEmpBankAccountDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpBankAccountDtlResp empBankAccountDtlResp = new EmpBankAccountDtlResp();
        List<EmpBankAccountDtlEntity> empBankAccountDtlEntites = empBankAccountDtlRepository
                .findEmpBankAccounts(projEmpRegisterGetReq.getEmpId(), projEmpRegisterGetReq.getStatus());
        for (EmpBankAccountDtlEntity empBankAccountDtlEntity : empBankAccountDtlEntites) {
            empBankAccountDtlResp.getEmpBankAccountDtlTOs()
                    .add(EmpBankAccountDtlHandler.convertEntityToPOJO(empBankAccountDtlEntity));
        }
        return empBankAccountDtlResp;
    }

    public void saveEmpBankAccountDetails(EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq) {
        List<EmpBankAccountDtlEntity> entites = EmpBankAccountDtlHandler
                .convertPOJOsToEntities(empBankAccountDetailsSaveReq.getEmpBankAccountDtlTOs(), empRegisterRepository);
        empBankAccountDtlRepository.save(entites);
    }

    public void deactivateEmpBankAccountDetails(EmpBankAccDeactivateReq empBankAccDeactivateReq) {
        empBankAccountDtlRepository.deactivateEmpBankAccounts(empBankAccDeactivateReq.getEmpBankAccountIds(),
                empBankAccDeactivateReq.getStatus());
    }

}
