package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;

public interface EmpBankAccountDetailsService {

    EmpBankAccountDtlResp getEmpBankAccountDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void deactivateEmpBankAccountDetails(EmpBankAccDeactivateReq empBankAccDeactivateReq);

    void saveEmpBankAccountDetails(EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq);

}
