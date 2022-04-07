package com.rjtech.register.controller.emp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.emp.EmpPayDeductionService;
import com.rjtech.register.service.handler.emp.EmpPayDeductionDetailHandler;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpPayDeductionController {

    @Autowired
    private EmpPayDeductionService empPayDeductionService;

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PAY_DEDUCTIONS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> getEmpPayDeductions(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpPayDeductionResp empPayDeductionResp = empPayDeductionService.getEmpPayDeductions(projEmpRegisterGetReq);

        ProjEmpServiceHistoryReq projEmpServiceHistoryReq = new ProjEmpServiceHistoryReq();
        projEmpServiceHistoryReq.setEmpId(projEmpRegisterGetReq.getEmpId());
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        Map<Long, Boolean> exteingServiceHistoryIdMap = new HashMap<Long, Boolean>();
        if (CommonUtil.isListHasData(resp.getProjEmpRegisterTOs())) {
            for (EmpPayDeductionTO payDeductionTO : empPayDeductionResp.getEmpPayDeductionTOs()) {
                exteingServiceHistoryIdMap.put(payDeductionTO.getEmpProjId(), true);
            }
            ProjEmpRegisterTO projEmpRegisterTO = resp.getProjEmpRegisterTOs().get(0);
            if (exteingServiceHistoryIdMap != null && exteingServiceHistoryIdMap.size() > 0
                    && !exteingServiceHistoryIdMap.containsKey(projEmpRegisterTO.getId())) {
                EmpPayDeductionDetailHandler.populatePayDeductionTO(empPayDeductionResp, projEmpRegisterTO);
            }
            if (!CommonUtil.isListHasData(empPayDeductionResp.getEmpPayDeductionTOs())) {
                EmpPayDeductionDetailHandler.populatePayDeductionTO(empPayDeductionResp, projEmpRegisterTO);
            }
        }

        return new ResponseEntity<EmpPayDeductionResp>(empPayDeductionResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PAY_DEDUCTION_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> getEmpPayDeductionDetails(@RequestBody EmpRegisterReq empRegisterReq) {
        EmpPayDeductionResp empPayDeductionResp = empPayDeductionService.getEmpPayDeductionDetails(empRegisterReq);
        return new ResponseEntity<EmpPayDeductionResp>(empPayDeductionResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_PAY_DEDUCTIONS, method = RequestMethod.POST)
    public ResponseEntity<EmpPayDeductionResp> saveEmpPayDeductions(
            @RequestBody EmpPayDeductionSaveReq empPayDeductionSaveReq) {
        empPayDeductionService.saveEmpPayDeductions(empPayDeductionSaveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setProjId(empPayDeductionSaveReq.getProjId());
        projEmpRegisterGetReq.setEmpId(empPayDeductionSaveReq.getEmpId());
        projEmpRegisterGetReq.setStatus(empPayDeductionSaveReq.getStatus());
        EmpPayDeductionResp empPayDeductionResp = empPayDeductionService.getEmpPayDeductions(projEmpRegisterGetReq);
        empPayDeductionResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpPayDeductionResp>(empPayDeductionResp, HttpStatus.OK);
    }
}
