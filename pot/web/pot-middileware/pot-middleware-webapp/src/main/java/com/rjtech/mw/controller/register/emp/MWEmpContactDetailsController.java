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
import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpContactDetailsController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_CONTACT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> getEmpContactDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        return new ResponseEntity<EmpContactDetailsResp>(mwRegisterService.getEmpContactDetails(projEmpRegisterGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_CONTACTS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> deactivateEmpContactDetails(
            @RequestBody EmpContactsDeactiveReq empContactsDeactiveReq) {

        return new ResponseEntity<EmpContactDetailsResp>(
                mwRegisterService.deactivateEmpContactDetails(empContactsDeactiveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_CONTACT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> saveEmpContactDetails(
            @RequestBody EmpContactSaveReeq empContactSaveReq) {

        return new ResponseEntity<EmpContactDetailsResp>(mwRegisterService.saveEmpContactDetails(empContactSaveReq),
                HttpStatus.OK);
    }
}
