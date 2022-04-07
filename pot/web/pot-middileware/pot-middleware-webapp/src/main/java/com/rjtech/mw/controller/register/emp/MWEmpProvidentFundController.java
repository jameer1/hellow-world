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
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.projsettings.req.ProjGeneralsGetReq;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpProvidentFundController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjectSettingsService mwProjectSettingsService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PROVIDENT_FUNDS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> getEmpProvidentFunds(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        ProjGeneralsGetReq projGeneralsGetReq = new ProjGeneralsGetReq();
        projGeneralsGetReq.setProjId(projEmpRegisterGetReq.getProjId());
        projGeneralsGetReq.setStatus(projEmpRegisterGetReq.getStatus());
        EmpProvidentFundResp empProvidentFundResp = new EmpProvidentFundResp();
        empProvidentFundResp = mwRegisterService.getEmpProvidentFunds(projEmpRegisterGetReq);
        empProvidentFundResp.setProjGeneralLabelTO(
                ProjGenHandler.populateGeneralTO(projEmpRegisterGetReq.getProjId(), mwProjectSettingsService));
        return new ResponseEntity<EmpProvidentFundResp>(empProvidentFundResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PROVIDENT_FUND_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> getEmpProvidentFundDetails(@RequestBody EmpRegisterReq empRegisterReq) {
        return new ResponseEntity<EmpProvidentFundResp>(mwRegisterService.getEmpProvidentFundDetails(empRegisterReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_PROVIDENT_FUNDS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> saveEmpProvidentFunds(
            @RequestBody EmpProvidentFundSaveReq empProvidentFundSaveReq) {
        return new ResponseEntity<EmpProvidentFundResp>(
                mwRegisterService.saveEmpProvidentFunds(empProvidentFundSaveReq), HttpStatus.OK);
    }
}
