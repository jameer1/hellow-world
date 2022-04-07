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
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpMedicalHistoryController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpMedicalHistoryResp> getEmpMedicalHistory(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
    	EmpMedicalHistoryResp empMedicalHistoryResp = mwRegisterService.getEmpMedicalHistory(projEmpRegisterGetReq);
    	System.out.println("size:"+empMedicalHistoryResp.getEmpMedicalHistoryTOs().size());
        return new ResponseEntity<EmpMedicalHistoryResp>(empMedicalHistoryResp,
                HttpStatus.OK);
    }

    /*@RequestMapping(value = RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpMedicalHistoryResp> saveEmpMedicalHistory(
            @RequestBody EmpMedicalHistorySaveReq empMedicalHistorySaveReq) {
        return new ResponseEntity<EmpMedicalHistoryResp>(
                mwRegisterService.saveEmpMedicalHistory(empMedicalHistorySaveReq), HttpStatus.OK);

    }*/
    
    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpMedicalHistoryResp> saveEmpMedicalHistory( MultipartFile[] files, 
            String empMedicalHistoryStr ) {
    	System.out.println("saveEmpMedicalHistory from MWEmpMedicalHistoryController");
    	System.out.println(empMedicalHistoryStr);
    	EmpMedicalHistorySaveReq empMedicalHistorySaveReq = AppUtils.fromJson( empMedicalHistoryStr,
    			EmpMedicalHistorySaveReq.class );
    	System.out.println("empMedicalHistorySaveReq result:");
    	System.out.println(empMedicalHistorySaveReq);    	
        return new ResponseEntity<EmpMedicalHistoryResp>( mwRegisterService.saveEmpMedicalHistory( files, empMedicalHistorySaveReq ), HttpStatus.OK );
    }
}
