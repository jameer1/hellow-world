package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;

public interface EmpChargeOutRateService {

    EmpChargeOutRateResp getEmpChargeOutRates(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void saveEmpChargeOutRates(EmpChargeOutRateSaveReq empChargeOutRateSaveReq);

}
