package com.rjtech.register.service.emp;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.req.EmpEnrollmentSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;

public interface EmpEnrollmentService {

    EmpEnrollmentResp getEmpEnrollments(EmpEnrollmentGetReq empEnrollmentGetReq);

    void saveEmpEnrollments(EmpEnrollmentSaveReq empEnrollmentSaveReq, MultipartFile file);

    EmpServiceHistoryResp getEmpServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq);

    EmpServiceHistoryResp getEmpLatestServiceHistory(ProjEmpServiceHistoryReq projEmpServiceHistoryReq);

    void saveEmpServiceHistory(ProjEmpRegistersSaveReq projEmpRegistersSaveReq);

    EmpEnrollmentDtlTO getEmpEnrollment(long enrollId);

}
