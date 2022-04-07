package com.rjtech.register.service.emp;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpRegisterResp;

public interface EmpDocsCommonService {

	void saveEmployeeDocs( MultipartFile[] files, EmpRegisterReq empRegisterReq ) throws IOException;
	
	EmpRegisterResp getEmployeeDocs( EmpRegisterReq empRegisterReq );
}
