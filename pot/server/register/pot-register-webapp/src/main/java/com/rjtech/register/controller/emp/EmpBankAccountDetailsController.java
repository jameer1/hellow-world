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
import com.rjtech.register.emp.dto.EmpBankAccountDtlTO;
import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;
import com.rjtech.register.service.emp.EmpBankAccountDetailsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpBankAccountDetailsController {

    @Autowired
    private EmpBankAccountDetailsService empBankAccountDetailsService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_BANK_ACCOUNT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> getEmpBanlAccountDetails(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpBankAccountDtlResp details = empBankAccountDetailsService.getEmpBankAccountDetails(projEmpRegisterGetReq);
        return new ResponseEntity<EmpBankAccountDtlResp>(details, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_BANK_ACCOUNTS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> deactivateEmpBanlAccountDetails(
            @RequestBody EmpBankAccDeactivateReq empBankAccDeactivateReq) {
        empBankAccountDetailsService.deactivateEmpBankAccountDetails(empBankAccDeactivateReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projEmpRegisterGetReq.setEmpId(null);
        EmpBankAccountDtlResp empBankAccountDtlResp = empBankAccountDetailsService
                .getEmpBankAccountDetails(projEmpRegisterGetReq);
        empBankAccountDtlResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpBankAccountDtlResp>(empBankAccountDtlResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_BANK_ACCOUNT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpBankAccountDtlResp> saveEmpBankAccountDetails(
            @RequestBody EmpBankAccountDetailsSaveReq empBankAccountDetailsSaveReq) {

        empBankAccountDetailsService.saveEmpBankAccountDetails(empBankAccountDetailsSaveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        for (EmpBankAccountDtlTO empBankAccountDtlTO : empBankAccountDetailsSaveReq.getEmpBankAccountDtlTOs()) {
            projEmpRegisterGetReq.setEmpId(empBankAccountDtlTO.getEmpRegDtlId());
        }
        EmpBankAccountDtlResp empBankAccountDtlResp = empBankAccountDetailsService
                .getEmpBankAccountDetails(projEmpRegisterGetReq);
        empBankAccountDtlResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpBankAccountDtlResp>(empBankAccountDtlResp, HttpStatus.OK);
    }

}
