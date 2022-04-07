package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpNokResp;

public interface EmpNokService {

    void saveEmpNok(EmpNokSaveReq empNokSaveReq);

    EmpNokResp getEmpNok(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void deactivateEmpNok(EmpNokDeactiveReq empNokDeactiveReq);

}
