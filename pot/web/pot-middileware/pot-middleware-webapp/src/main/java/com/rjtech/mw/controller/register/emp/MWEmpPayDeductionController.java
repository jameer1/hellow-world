package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.controller.projsettings.handler.ProjGenHandler;
import com.rjtech.mw.service.projectsettings.MWProjectSettingsService;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpPayDeductionController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PAY_DEDUCTIONS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> getEmpPayDeductions(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projEmpRegisterGetReq.getProjId());
        projGeneralsGetReq.setStatus(projEmpRegisterGetReq.getStatus());
        EmpPayDeductionResp empPayDeductionResp = new EmpPayDeductionResp();
        empPayDeductionResp = mwRegisterService.getEmpPayDeductions(projEmpRegisterGetReq);
        empPayDeductionResp.setProjGeneralLabelTO(
                ProjGenHandler.populateGeneralTO(projEmpRegisterGetReq.getProjId(), mwProjectSettingsService));
        return new ResponseEntity<EmpPayDeductionResp>(empPayDeductionResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PAY_DEDUCTION_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> getEmpPayDeductionDetails(@RequestBody EmpRegisterReq empRegisterReq) {
        return new ResponseEntity<EmpPayDeductionResp>(mwRegisterService.getEmpPayDeductionDetails(empRegisterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_PAY_DEDUCTIONS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> saveEmpPayDeductions(
            @RequestBody EmpPayDeductionSaveReq empPayDeductionSaveReq) {
        return new ResponseEntity<EmpPayDeductionResp>(mwRegisterService.saveEmpPayDeductions(empPayDeductionSaveReq),
                HttpStatus.OK);
    }

}
