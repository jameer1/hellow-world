package com.rjtech.register.service.emp;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.document.dto.ProjDocFileTO;

public interface RegisterDownloadService {

	ProjDocFileTO downloadRegisterDocs( Long recordId, String category ) throws IOException;
	
}
