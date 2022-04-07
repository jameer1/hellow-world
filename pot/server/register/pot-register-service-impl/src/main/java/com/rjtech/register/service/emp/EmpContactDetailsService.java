package com.rjtech.register.service.emp;

import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;

public interface EmpContactDetailsService {

    EmpContactDetailsResp getEmpContactDetails(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void deactivateEmpContactDetails(EmpContactsDeactiveReq empContactsDeactiveReq);

    void saveEmpContactDetails(EmpContactSaveReeq empContactSaveReq);

}
