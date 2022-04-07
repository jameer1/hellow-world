package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;
import com.rjtech.register.service.emp.EmpContactDetailsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpContactDetailsController {

    @Autowired
    private EmpContactDetailsService empContactDetailsService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_CONTACT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> getEmpContactDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        return new ResponseEntity<EmpContactDetailsResp>(
                empContactDetailsService.getEmpContactDetails(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_CONTACTS_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> deactivateEmpContactDetails(
            @RequestBody EmpContactsDeactiveReq empContactsDeactiveReq) {
        empContactDetailsService.deactivateEmpContactDetails(empContactsDeactiveReq);
        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpContactDetailsResp empContactDetailsResp = empContactDetailsService
                .getEmpContactDetails(projEmpRegisterGetReq);
        empContactDetailsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpContactDetailsResp>(empContactDetailsResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_CONTACT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpContactDetailsResp> saveEmpContactDetails(
            @RequestBody EmpContactSaveReeq empContactSaveReq) {
        empContactDetailsService.saveEmpContactDetails(empContactSaveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projEmpRegisterGetReq.setEmpId(empContactSaveReq.getEmpId());
        EmpContactDetailsResp empContactDetailsResp = empContactDetailsService
                .getEmpContactDetails(projEmpRegisterGetReq);
        empContactDetailsResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpContactDetailsResp>(empContactDetailsResp, HttpStatus.OK);
    }

}
