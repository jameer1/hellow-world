package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;
import com.rjtech.register.service.emp.EmpMedicalHistoryService;
import java.io.IOException;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpMedicalHistoryController {

    @Autowired
    private EmpMedicalHistoryService empMedicalHistoryService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpMedicalHistoryResp> getEmpMedicalHistory(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<EmpMedicalHistoryResp>(
                empMedicalHistoryService.getEmpMedicalHistory(projEmpRegisterGetReq), HttpStatus.OK);
    }

    /*@RequestMapping(value = RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpMedicalHistoryResp> saveEmpMedicalHistory(
            @RequestBody EmpMedicalHistorySaveReq empMedicalHistorySaveReq) {

        empMedicalHistoryService.saveEmpMedicalHistory(empMedicalHistorySaveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setEmpId(empMedicalHistorySaveReq.getEmpId());
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpMedicalHistoryResp empMedicalHistoryResp = empMedicalHistoryService
                .getEmpMedicalHistory(projEmpRegisterGetReq);
        empMedicalHistoryResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpMedicalHistoryResp>(empMedicalHistoryResp, HttpStatus.OK);
    }*/
     @RequestMapping(value = RegisterURLConstants.SAVE_EMP_MEDICAL_HISTORY, method = RequestMethod.POST)
     public ResponseEntity<EmpMedicalHistoryResp> saveEmpMedicalHistory( MultipartFile[] files, 
             String empMedicalHistoryStr ) throws IOException {
    	 EmpMedicalHistorySaveReq empMedicalHistorySaveReq = AppUtils.fromJson( empMedicalHistoryStr,
    			 EmpMedicalHistorySaveReq.class );
    	 System.out.println("saveEmpMedicalHistory function from EmpMedicalHistoryContoller");
    	 System.out.println(empMedicalHistorySaveReq);
         empMedicalHistoryService.saveEmpMedicalHistory( files, empMedicalHistorySaveReq );

         ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
         projEmpRegisterGetReq.setEmpId( empMedicalHistorySaveReq.getEmpId() );
         projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
         EmpMedicalHistoryResp empMedicalHistoryResp = empMedicalHistoryService
                 .getEmpMedicalHistory(projEmpRegisterGetReq);
         empMedicalHistoryResp.cloneAppResp(CommonUtil.getSaveAppResp());
         return new ResponseEntity<EmpMedicalHistoryResp>(empMedicalHistoryResp, HttpStatus.OK);
     }
}
