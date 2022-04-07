package com.rjtech.register.controller.emp;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.service.emp.RegisterDownloadService;
import com.rjtech.document.dto.ProjDocFileTO;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class RegisterDownloadController {

    @Autowired
    private RegisterDownloadService registerDownloadService;
    
    @GetMapping(value = RegisterURLConstants.DOWNLOAD_REGISTER_DOCS)
    public String[] downloadRegisterDocs( @RequestParam("recordId") Long recordId, @RequestParam("category") String category ) throws IOException {
    	ProjDocFileTO fileTo = registerDownloadService.downloadRegisterDocs( recordId, category );
    	String file_info[] = { fileTo.getName(), fileTo.getFileSize(), fileTo.getFileType(), fileTo.getFolderPath() };
    	return file_info;
    }
}
