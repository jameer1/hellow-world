package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;

public interface EmpNonRegularPayService {

    EmpPaybleRateResp getEmpNonRegularPaybleRates(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    EmpPaybleRateResp getEmpNonRegularPaybleRateDetails(EmpRegisterReq empRegisterReq);

    void saveEmpNonRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq);

}
