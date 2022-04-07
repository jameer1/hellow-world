package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.controller.projsettings.handler.ProjGenHandler;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpRegularPayController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpRegularPaybleRates(@RequestBody EmpRegisterReq empRegisterReq) {
    	//System.out.println("==middlelayer=serviceimpl==getEmpRegularPaybleRates");
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(empRegisterReq.getProjId());
        //System.out.println("==middlelayer=serviceimpl==projGeneralsGetReq.setProjId=="+projGeneralsGetReq.getProjId());
        projGeneralsGetReq.setStatus(empRegisterReq.getStatus());
        //System.out.println("==middlelayer=serviceimpl==projGeneralsGetReq.setProjId=="+projGeneralsGetReq.getStatus());
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();
        empPaybleRateResp = mwRegisterService.getEmpRegularPaybleRates(empRegisterReq);
        empPaybleRateResp.setProjGeneralLabelTO(
                ProjGenHandler.populateGeneralTO(empRegisterReq.getProjId(), mwProjectSettingsService));
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpRegularPaybleRateDetails(
            @RequestBody EmpRegisterReq empRegisterReq) {
    	     //System.out.println("==middlelayer===controller==getEmpRegularPaybleRateDetails");
        return new ResponseEntity<EmpPaybleRateResp>(mwRegisterService.getEmpRegularPaybleRateDetails(empRegisterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> saveEmpRegularPaybleRates(
            @RequestBody EmpPayRatesSaveReq empPayRatesSaveReq) {
    	//System.out.println("==middlelayer===controller==saveEmpRegularPaybleRates");
        return new ResponseEntity<EmpPaybleRateResp>(mwRegisterService.saveEmpRegularPaybleRates(empPayRatesSaveReq),
                HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.SAVE_EMPLOYEE_DOCS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterResp> saveEmployeeDocs( MultipartFile[] files, String employeeDocsStr ) {
    	System.out.println("saveEmployeeDocs from MWEmpRegularPayController");
    	EmpRegisterReq empRegisterReq = AppUtils.fromJson(employeeDocsStr,
    			EmpRegisterReq.class);
        return new ResponseEntity<EmpRegisterResp>(mwRegisterService.saveEmployeeDocs(files, empRegisterReq), HttpStatus.OK);        
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_EMPLOYEE_DOCS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterResp> getEmployeeDocs( @RequestBody EmpRegisterReq empRegisterReq ) {
    	System.out.println("getEmployeeDocs from MWEmpRegularPayController");
    	/*EmpRegisterReq empRegisterReq = AppUtils.fromJson(employeeDocsStr,
    			EmpRegisterReq.class);*/
        return new ResponseEntity<EmpRegisterResp>( mwRegisterService.getEmployeeDocs( empRegisterReq ), HttpStatus.OK );        
    }
}
