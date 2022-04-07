package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;

public interface EmpPayDeductionService {

    EmpPayDeductionResp getEmpPayDeductions(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpPayDeductionResp getEmpPayDeductionDetails(EmpRegisterReq empRegisterReq);

    void saveEmpPayDeductions(EmpPayDeductionSaveReq empPayDeductionSaveReq);

}
