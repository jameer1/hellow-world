package com.rjtech.register.service.emp;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;

public interface EmpMedicalHistoryService {

    EmpMedicalHistoryResp getEmpMedicalHistory(ProjEmpRegisterGetReq projEmpRegisterGetReq);

    void saveEmpMedicalHistory( MultipartFile[] files, EmpMedicalHistorySaveReq empMedicalHistorySaveReq ) throws IOException;

}
