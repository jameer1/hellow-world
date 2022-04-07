package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;

public interface EmpProvidentFundService {

    EmpProvidentFundResp getEmpProvidentFunds(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpProvidentFundResp getEmpProvidentFundDetails(EmpRegisterReq empRegisterReq);

    void saveEmpProvidentFunds(EmpProvidentFundSaveReq empProvidentFundSaveReq);

}
