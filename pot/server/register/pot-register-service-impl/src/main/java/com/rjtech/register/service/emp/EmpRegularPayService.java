package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;

public interface EmpRegularPayService {

    EmpPaybleRateResp getEmpRegularPaybleRates(EmpRegisterReq empRegisterReq);

    EmpPaybleRateResp getEmpRegularPaybleRateDetails(EmpRegisterReq empRegisterReq);

    void saveEmpRegularPaybleRates(EmpPayRatesSaveReq empPayRatesSaveReq);

}
