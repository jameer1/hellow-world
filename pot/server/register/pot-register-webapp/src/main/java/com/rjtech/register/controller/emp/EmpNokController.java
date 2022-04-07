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
import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.resp.EmpNokResp;
import com.rjtech.register.service.emp.EmpNokService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpNokController {

    @Autowired
    private EmpNokService empNokService;

    @RequestMapping(value = RegisterURLConstants.SAVE_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> saveEmpNok(@RequestBody EmpNokSaveReq empNokSaveReq) {
        empNokService.saveEmpNok(empNokSaveReq);
        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(empNokSaveReq.getStatus());
        projEmpRegisterGetReq.setEmpId(empNokSaveReq.getEmpId());
        EmpNokResp empNokResp = empNokService.getEmpNok(projEmpRegisterGetReq);
        empNokResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpNokResp>(empNokResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.DEACTIVATE_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> deactivateEmpNok(@RequestBody EmpNokDeactiveReq empNokDeactiveReq) {
        empNokService.deactivateEmpNok(empNokDeactiveReq);

        ProjEmpRegisterGetReq projEmpRegisterGetReq = new ProjEmpRegisterGetReq();
        projEmpRegisterGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        projEmpRegisterGetReq.setEmpId(null);
        EmpNokResp empNokResp = empNokService.getEmpNok(projEmpRegisterGetReq);
        empNokResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpNokResp>(empNokResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_EMP_NOK, method = RequestMethod.POST)
    public ResponseEntity<EmpNokResp> getEmpNok(@RequestBody ProjEmpRegisterGetReq projEmpRegisterGetReq) {
        EmpNokResp empNokResp = empNokService.getEmpNok(projEmpRegisterGetReq);
        return new ResponseEntity<EmpNokResp>(empNokResp, HttpStatus.OK);
    }

}
