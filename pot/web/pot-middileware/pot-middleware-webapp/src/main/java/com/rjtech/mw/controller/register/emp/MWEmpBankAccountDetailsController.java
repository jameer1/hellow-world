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
import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpBankAccountDetailsController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_BANK_ACCOUNT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> getEmpBanlAccountDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        return new ResponseEntity<EmpBankAccountDtlResp>(
                mwRegisterService.getEmpBanlAccountDetails(projEmpRegisterGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_BANK_ACCOUNTS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> deactivateEmpBanlAccountDetails(
            @RequestBody EmpBankAccDeactivateReq empBankAccDeactivateReq) {
        return new ResponseEntity<EmpBankAccountDtlResp>(
                mwRegisterService.deactivateEmpBanlAccountDetails(empBankAccDeactivateReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_BANK_ACCOUNT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> saveEmpBanlAccountDetails(
            @RequestBody EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq) {
        return new ResponseEntity<EmpBankAccountDtlResp>(
                mwRegisterService.saveEmpBanlAccountDetails(empBankAccountDetailsSaveReq), HttpStatus.OK);
    }

}
