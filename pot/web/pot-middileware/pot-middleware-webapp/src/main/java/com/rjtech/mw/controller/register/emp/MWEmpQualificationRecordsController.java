package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpQualificationRecordsController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_QUALIFICATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<EmpQualificationRecordsResp> getEmpQualificationRecords( @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq ) {
    	System.out.println("getEmpQualificationRecords function of MWEmpQualificationRecordsController");
    	EmpQualificationRecordsResp empQualificationRecordsResp = mwRegisterService.getEmpQualificationRecords( projEmpRegisterGetReq );
    	System.out.println(empQualificationRecordsResp);
    	System.out.println("size in getEmpQualificationRecords function of MWEmpQualificationRecordsController:"+empQualificationRecordsResp.getEmpQualificationRecordsTOs().size());
        return new ResponseEntity<EmpQualificationRecordsResp>( empQualificationRecordsResp,
                HttpStatus.OK );
    }
    
    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_QUALIFICATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<EmpQualificationRecordsResp> saveEmpQualificationRecords( MultipartFile[] files, 
            String empQualRecordsStr ) {
    	System.out.println("saveEmpQualificationRecords from MWEmpQualificationRecordsController");
    	System.out.println(empQualRecordsStr);
    	EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq = AppUtils.fromJson( empQualRecordsStr,
    			EmpQualificationRecordsSaveReq.class );
    	return new ResponseEntity<EmpQualificationRecordsResp>( mwRegisterService.saveEmpQualificationRecords( files, empQualificationRecordsSaveReq ), HttpStatus.OK );	
    }
}
