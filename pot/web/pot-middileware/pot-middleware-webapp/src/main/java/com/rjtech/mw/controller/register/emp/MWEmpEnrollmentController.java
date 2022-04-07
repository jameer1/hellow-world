package com.rjtech.mw.controller.register.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWEmpEnrollmentController {

    @Autowired
    private MWEmpRegisterService mwRegisterService;

    @PostMapping(value = RegisterURLConstants.GET_EMP_ENROLLMENTS)
    public ResponseEntity<EmpEnrollmentResp> getEmpEnrollments(@RequestBody EmpEnrollmentGetReq empEnrollmentGetReq) {
        return new ResponseEntity<>(mwRegisterService.getEmpEnrollments(empEnrollmentGetReq), HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_ENROLLMENTS)
    public ResponseEntity<EmpEnrollmentResp> saveEmpEnrollments(MultipartFile file, String empEnrollmentSaveReq) {
        return new ResponseEntity<>(mwRegisterService.saveEmpEnrollments(file, empEnrollmentSaveReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.DOWNLOAD_ENROLLMENT_CONTRACT)
    public ResponseEntity<ByteArrayResource> downloadEnrollmentContract(@RequestParam("enrollId") Long enrollId) {
        return mwRegisterService.downloadEnrollmentContract(enrollId);
    }

}
