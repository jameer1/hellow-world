package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.register.service.emp.EmpQualificationRecordsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpQualificationRecordsController {

    @Autowired
    private EmpQualificationRecordsService empQualificationRecordsService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_QUALIFICATION_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<EmpQualificationRecordsResp> getEmpQualificationRecords(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq ) {
    	System.out.println("getEmpQualificationRecords function of EmpQualificationRecordsController class");
    	EmpQualificationRecordsResp empQualificationRecordsResp = empQualificationRecordsService.getEmpQualificationRecords( projEmpRegisterGetReq );
    	System.out.println("getEmpQualificationRecords sie:"+empQualificationRecordsResp.getEmpQualificationRecordsTOs().size());
        return new ResponseEntity<EmpQualificationRecordsResp>( empQualificationRecordsResp
        		, HttpStatus.OK );
    }
      
     @RequestMapping(value = RegisterURLConstants.SAVE_EMP_QUALIFICATION_RECORDS, method = RequestMethod.POST)
     public ResponseEntity<EmpQualificationRecordsResp> saveEmpQualificationRecords( MultipartFile[] files, 
             String empQualRecordsStr ) throws IOException {
    	 EmpQualificationRecordsSaveReq empQualificationRecordsSaveReq = AppUtils.fromJson( empQualRecordsStr,
    			 EmpQualificationRecordsSaveReq.class );
    	 System.out.println("saveEmpQualificationRecords function from EmpQualificationRecordsController");
    	 System.out.println(empQualificationRecordsSaveReq);
    	 System.out.println(empQualRecordsStr);
    	 empQualificationRecordsService.saveEmpQualificationRecords( files, empQualificationRecordsSaveReq );

         /*ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
         projEmpRegisterGetReq.setEmpId( empMedicalHistorySaveReq.getEmpId() );
         projEmpRegisterGetReq.setStatus( StatusCodes.ACTIVE.getValue() );
         EmpQualificationRecordsResp empQualificationRecordsResp = empQualificationRecordsService
                 .getEmpMedicalHistory( projEmpRegisterGetReq );
         empMedicalHistoryResp.cloneAppResp(CommonUtil.getSaveAppResp());*/
    	 EmpQualificationRecordsResp empQualificationRecordsResp = new EmpQualificationRecordsResp();
    	 empQualificationRecordsResp.cloneAppResp(CommonUtil.getSaveAppResp());
         return new ResponseEntity<EmpQualificationRecordsResp>( empQualificationRecordsResp, HttpStatus.OK );
     }
}
