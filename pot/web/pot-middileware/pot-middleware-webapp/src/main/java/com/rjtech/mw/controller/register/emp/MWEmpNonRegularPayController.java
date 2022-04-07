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
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpNonRegularPayController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpNonRegularPaybleRates(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projEmpRegisterGetReq.getProjId());
        projGeneralsGetReq.setStatus(projEmpRegisterGetReq.getStatus());
        EmpPaybleRateResp empPaybleRateResp = new EmpPaybleRateResp();
        empPaybleRateResp = mwRegisterService.getEmpNonRegularPaybleRates(projEmpRegisterGetReq);
        empPaybleRateResp.setProjGeneralLabelTO(
                ProjGenHandler.populateGeneralTO(projEmpRegisterGetReq.getProjId(), mwProjectSettingsService));
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpNonRegularPaybleRateDetails(
            @RequestBody EmpRegisterReq empRegisterReq) {
        return new ResponseEntity<EmpPaybleRateResp>(
                mwRegisterService.getEmpNonRegularPaybleRateDetails(empRegisterReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_NON_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> saveEmpNonRegularPaybleRates(
            @RequestBody EmpPayRatesSaveReq empPayRatesSaveReq) {
        return new ResponseEntity<EmpPaybleRateResp>(mwRegisterService.saveEmpNonRegularPaybleRates(empPayRatesSaveReq),
                HttpStatus.OK);
    }
}
