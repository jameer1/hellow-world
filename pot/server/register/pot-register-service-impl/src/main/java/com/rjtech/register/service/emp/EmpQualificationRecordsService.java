package com.rjtech.register.service.emp;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;

public interface EmpQualificationRecordsService {

	EmpQualificationRecordsResp getEmpQualificationRecords( ProjEmpRegisterGetReq projEmpRegisterGetReq );	

    void saveEmpQualificationRecords( MultipartFile[] files, EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq ) throws IOException;
}
