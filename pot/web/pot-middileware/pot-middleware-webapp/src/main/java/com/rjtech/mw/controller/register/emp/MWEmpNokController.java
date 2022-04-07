package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpNokResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpNokController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> getEmpNok(@RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        return new ResponseEntity<EmpNokResp>(mwRegisterService.getEmpNok(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> deactivateEmpNok(@RequestBody EmpNokDeactiveReq empNokDeactiveReq) {
        return new ResponseEntity<EmpNokResp>(mwRegisterService.deactivateEmpNok(empNokDeactiveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> saveEmpNok(@RequestBody EmpNokSaveReq empNokSaveReq) {
        return new ResponseEntity<EmpNokResp>(mwRegisterService.saveEmpNok(empNokSaveReq), HttpStatus.OK);
    }

}
