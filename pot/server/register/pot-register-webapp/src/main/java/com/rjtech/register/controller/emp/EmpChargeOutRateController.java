package com.rjtech.register.controller.emp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpChargeOutRateService;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.handler.emp.EmpChargeOutRateHandler;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpChargeOutRateController {

    @Autowired
    private EmpChargeOutRateService empChargeOutRateService;

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_CHARGEOUT_RATES)
    public ResponseEntity<EmpChargeOutRateResp> getEmpChargeOutRates(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpChargeOutRateResp empChargeOutRateResp = empChargeOutRateService.getEmpChargeOutRates(projEmpRegisterGetReq);

        ProjEmpServiceHistoryReq projEmpServiceHistoryReq = new ProjEmpServiceHistoryReq();
        projEmpServiceHistoryReq.setEmpId(projEmpRegisterGetReq.getEmpId());
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpServiceHistory(projEmpServiceHistoryReq);
        if (CommonUtil.isListHasData(resp.getProjEmpRegisterTOs())) {
            Map<Long, Boolean> exteingServiceHistoryIdMap = new HashMap<>();
            for (EmpChargeOutRateTO chargeOutRatesTO : empChargeOutRateResp.getEmpChargeOutRateTOs()) {
                exteingServiceHistoryIdMap.put(chargeOutRatesTO.getEmpProjId(), true);
            }
            for (ProjEmpRegisterTO projEmpRegisterTO : resp.getProjEmpRegisterTOs()) {
                if (!exteingServiceHistoryIdMap.containsKey(projEmpRegisterTO.getId())) {
                    EmpChargeOutRateHandler.populateChargeOutRateTO(empChargeOutRateResp, projEmpRegisterTO);
                }
                if (!CommonUtil.isListHasData(empChargeOutRateResp.getEmpChargeOutRateTOs())) {
                    EmpChargeOutRateHandler.populateChargeOutRateTO(empChargeOutRateResp, projEmpRegisterTO);
                }
            }
        }
        return new ResponseEntity<>(empChargeOutRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_CHARGEOUT_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpChargeOutRateResp> saveEmpChargeOutRates(
            @RequestBody EmpChargeOutRateSaveReq empChargeOutRateSaveReq) {

        empChargeOutRateService.saveEmpChargeOutRates(empChargeOutRateSaveReq);
        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projEmpRegisterGetReq.setEmpId(empChargeOutRateSaveReq.getEmpId());
        EmpChargeOutRateResp empChargeOutRateResp = empChargeOutRateService.getEmpChargeOutRates(projEmpRegisterGetReq);
        empChargeOutRateResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<EmpChargeOutRateResp>(empChargeOutRateResp, HttpStatus.OK);
    }

}
