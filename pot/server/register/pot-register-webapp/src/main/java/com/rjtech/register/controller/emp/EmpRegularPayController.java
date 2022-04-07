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
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.emp.EmpRegularPayService;
import com.rjtech.register.service.handler.emp.EmpRegularPayRateHandler;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpRegularPayController {

    @Autowired
    private EmpRegularPayService empRegularPayService;

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpRegularPaybleRates(@RequestBody EmpRegisterReq empRegisterReq) {
    	System.out.println("===controller==getEmpRegularPaybleRates====");
        EmpPaybleRateResp empPaybleRateResp = empRegularPayService.getEmpRegularPaybleRates(empRegisterReq);

        ProjEmpServiceHistoryReq projEmpServiceHistoryReq = new ProjEmpServiceHistoryReq();
        projEmpServiceHistoryReq.setEmpId(empRegisterReq.getEmpId());
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        Map<Long, Boolean> exteingServiceHistoryIdMap = new HashMap<Long, Boolean>();
        if (CommonUtil.isListHasData(resp.getProjEmpRegisterTOs())) {
            for (EmpPaybleRateTO regularPaybleRateTO : empPaybleRateResp.getEmpPaybleRateTOs()) {
                exteingServiceHistoryIdMap.put(regularPaybleRateTO.getEmpProjId(), true);
            }
            ProjEmpRegisterTO projEmpRegisterTO = resp.getProjEmpRegisterTOs().get(0);
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

    @RequestMapping(value = RegisterURLConstants.GET_EMP_REGULAR_PAYBLE_RATE_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> getEmpRegularPaybleRateDetails(
            @RequestBody EmpRegisterReq empRegisterReq) {
    	System.out.println("=====controller=====getEmpRegularPaybleRateDetails");
        EmpPaybleRateResp empPaybleRateResp = empRegularPayService.getEmpRegularPaybleRateDetails(empRegisterReq);
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_REGULAR_PAYBLE_RATES, method = RequestMethod.POST)
    public ResponseEntity<EmpPaybleRateResp> saveEmpRegularPaybleRates(
            @RequestBody EmpPayRatesSaveReq empPayRatesSaveReq) {
        empRegularPayService.saveEmpRegularPaybleRates(empPayRatesSaveReq);

        EmpRegisterReq empRegisterReq = new EmpRegisterReq();
        empRegisterReq.setProjId(empPayRatesSaveReq.getProjId());
        empRegisterReq.setEmpId(empPayRatesSaveReq.getEmpId());
        empRegisterReq.setStatus(empPayRatesSaveReq.getStatus());
        EmpPaybleRateResp empPaybleRateResp = empRegularPayService.getEmpRegularPaybleRates(empRegisterReq);
        empPaybleRateResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpPaybleRateResp>(empPaybleRateResp, HttpStatus.OK);
    }
}
