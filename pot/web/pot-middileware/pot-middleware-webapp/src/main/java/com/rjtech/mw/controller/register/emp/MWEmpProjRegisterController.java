package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.controller.projsettings.handler.ProjGenHandler;
import com.rjtech.mw.service.projlib.MWProjLibService;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpProjRegisterController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_EMP_SERVICE_HISTORY_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> getEmpServiceHistory(
            @RequestBody ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = mwRegisterService.getEmpServiceHistory(projEmpServiceHistoryReq);
        resp.getProjectLibTO().setProjClassMap(ProjGenHandler.getUserProjEmpClassify(mwProjLibService));
        return new ResponseEntity<EmpServiceHistoryResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_EMP_LATEST_SERVICE_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> getEmpLatestServiceHistory(
            @RequestBody ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = mwRegisterService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        resp.getProjectLibTO().setProjClassMap(ProjGenHandler.getUserProjEmpClassify(mwProjLibService));
        return new ResponseEntity<EmpServiceHistoryResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_EMP_SERVICE_HISTORY_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> saveEmpServiceHistory(
            @RequestBody ProjEmpRegistersSaveReq projEmpRegistersSaveReq) {
        EmpServiceHistoryResp resp = mwRegisterService.saveEmpServiceHistory(projEmpRegistersSaveReq);
        resp.getProjectLibTO().setProjClassMap(ProjGenHandler.getUserProjEmpClassify(mwProjLibService));
        return new ResponseEntity<EmpServiceHistoryResp>(resp, HttpStatus.OK);
    }

}
