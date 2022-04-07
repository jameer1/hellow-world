package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWRegisterDownloadService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWRegisterDownloadController {

    @Autowired
    private MWRegisterDownloadService mwRegisterDownloadService;

    @GetMapping(value = RegisterURLConstants.DOWNLOAD_REGISTER_DOCS)
    public ResponseEntity<ByteArrayResource> downloadRegisterDocs( @RequestParam("recordId") Long recordId, @RequestParam("category") String category ) {
    	System.out.println("function:"+category);
    	System.out.println("downloadRegisterDocs function of MWRegisterDownloadController class");
        return mwRegisterDownloadService.downloadRegisterDocs( recordId, category );
    }
}
