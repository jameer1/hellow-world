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
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.emp.EmpNonRegularPayService;
import com.rjtech.register.service.handler.emp.EmpRegularPayRateHandler;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpNonRegularPayController {

    @Autowired
    private EmpNonRegularPayService empNonRegularPayService;

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpNonRegularPaybleRates(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpPaybleRateResp empPaybleRateResp = empNonRegularPayService
                .getEmpNonRegularPaybleRates(projEmpRegisterGetReq);

        ProjEmpServiceHistoryReq projEmpServiceHistoryReq = new ProjEmpServiceHistoryReq();
        projEmpServiceHistoryReq.setEmpId(projEmpRegisterGetReq.getEmpId());
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        Map<Long, Boolean> exteingServiceHistoryIdMap = new HashMap<Long, Boolean>();

        if (CommonUtil.isListHasData(resp.getProjEmpRegisterTOs())) {
            ProjEmpRegisterTO projEmpRegisterTO = resp.getProjEmpRegisterTOs().get(0);
            for (EmpPaybleRateTO regularPaybleRateTO : empPaybleRateResp.getEmpPaybleRateTOs()) {
                exteingServiceHistoryIdMap.put(regularPaybleRateTO.getEmpProjId(), true);
            }
            if (exteingServiceHistoryIdMap != null && exteingServiceHistoryIdMap.size() > 0
                    && !exteingServiceHistoryIdMap.containsKey(projEmpRegisterTO.getId())) {
                EmpRegularPayRateHandler.populateEmpPaybleTOFromEmpProjRegTO(empPaybleRateResp, projEmpRegisterTO);

            }
            if (!CommonUtil.isListHasData(empPaybleRateResp.getEmpPaybleRateTOs())) {
                EmpRegularPayRateHandler.populateEmpPaybleTOFromEmpProjRegTO(empPaybleRateResp, projEmpRegisterTO);

            }
        }
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NON_REGULAR_PAYBLE_RATE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpRegularPaybleRateDetails(
            @RequestBody EmpRegisterReq empRegisterReq) {
    	System.out.println("==getEmpRegularPaybleRateDetails==");
        EmpPaybleRateResp empPaybleRateResp = empNonRegularPayService.getEmpNonRegularPaybleRateDetails(empRegisterReq);
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_NON_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> saveEmpNonRegularPaybleRates(
            @RequestBody EmpPayRatesSaveReq empPayRatesSaveReq) {
        empNonRegularPayService.saveEmpNonRegularPaybleRates(empPayRatesSaveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setProjId(empPayRatesSaveReq.getProjId());
        projEmpRegisterGetReq.setEmpId(empPayRatesSaveReq.getEmpId());
        projEmpRegisterGetReq.setStatus(empPayRatesSaveReq.getStatus());
        EmpPaybleRateResp empPaybleRateResp = empNonRegularPayService
                .getEmpNonRegularPaybleRates(projEmpRegisterGetReq);
        empPaybleRateResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }
}
