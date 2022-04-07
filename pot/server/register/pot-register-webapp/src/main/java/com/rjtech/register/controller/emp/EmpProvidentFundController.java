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
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;
import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.emp.EmpProvidentFundService;
import com.rjtech.register.service.handler.emp.EmpProvidentFundDetailHandler;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpProvidentFundController {

    @Autowired
    private EmpProvidentFundService empProvidentFundService;

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PROVIDENT_FUNDS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> getEmpProvidentFunds(
            @RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {

        EmpProvidentFundResp empProvidentFundResp = empProvidentFundService.getEmpProvidentFunds(projEmpRegisterGetReq);
        ProjEmpServiceHistoryReq projEmpServiceHistoryReq = new ProjEmpServiceHistoryReq();
        projEmpServiceHistoryReq.setEmpId(projEmpRegisterGetReq.getEmpId());
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        Map<Long, Boolean> exteingServiceHistoryIdMap = new HashMap<Long, Boolean>();
        if (CommonUtil.isListHasData(resp.getProjEmpRegisterTOs())) {
            ProjEmpRegisterTO projEmpRegisterTO = resp.getProjEmpRegisterTOs().get(0);
            for (EmpProvidentFundTO empProvidentFundTO : empProvidentFundResp.getEmpProvidentFundTOs()) {
                exteingServiceHistoryIdMap.put(empProvidentFundTO.getEmpProjId(), true);
                empProvidentFundTO.setProjEmpRegisterTO(projEmpRegisterTO);
            }
            if (exteingServiceHistoryIdMap != null && exteingServiceHistoryIdMap.size() > 0
                    && !exteingServiceHistoryIdMap.containsKey(projEmpRegisterTO.getId())) {
                EmpProvidentFundDetailHandler.populateEmpProvidentFundTOFromEmpProjRegTO(empProvidentFundResp,
                        projEmpRegisterTO);
            }
            if (!CommonUtil.isListHasData(empProvidentFundResp.getEmpProvidentFundTOs())) {
                EmpProvidentFundDetailHandler.populateEmpProvidentFundTOFromEmpProjRegTO(empProvidentFundResp,
                        projEmpRegisterTO);
            }
        }
        return new ResponseEntity<EmpProvidentFundResp>(empProvidentFundResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_PROVIDENT_FUND_DTLS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> getEmpProvidentFundDetails(@RequestBody EmpRegisterReq empRegisterReq) {
        EmpProvidentFundResp empProvidentFundResp = empProvidentFundService.getEmpProvidentFundDetails(empRegisterReq);
        return new ResponseEntity<EmpProvidentFundResp>(empProvidentFundResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_PROVIDENT_FUNDS, method = RequestMethod.POST)
    public ResponseEntity<EmpProvidentFundResp> saveEmpProvidentFunds(
            @RequestBody EmpProvidentFundSaveReq empProvidentFundSaveReq) {
        empProvidentFundService.saveEmpProvidentFunds(empProvidentFundSaveReq);
        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setEmpId(empProvidentFundSaveReq.getEmpId());
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        EmpProvidentFundResp empProvidentFundResp = empProvidentFundService.getEmpProvidentFunds(projEmpRegisterGetReq);
        empProvidentFundResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpProvidentFundResp>(empProvidentFundResp, HttpStatus.OK);
    }

}
