package com.rjtech.register.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.req.EmpEnrollmentSaveReq;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.service.emp.EmpEnrollmentService;
import com.rjtech.register.service.emp.EmpRegisterService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpEnrollmentController extends RegisterCommonController {

    @Autowired
    private EmpEnrollmentService empEnrollmentService;

    @Autowired
    private EmpRegisterService empRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_EMP_ENROLLMENTS, method = RequestMethod.POST)
    public ResponseEntity<EmpEnrollmentResp> getEmpEnrollments(@RequestBody EmpEnrollmentGetReq empEnrollmentGetReq) {
        EmpEnrollmentResp resp = empEnrollmentService.getEmpEnrollments(empEnrollmentGetReq);
        populateOnLoadMap(resp);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_EMP_ENROLLMENTS)
    public ResponseEntity<EmpEnrollmentResp> saveEmpEnrollments(MultipartFile file, String clientRegReq) {
        EmpEnrollmentSaveReq empEnrollmentSaveReq = AppUtils.fromJson(clientRegReq, EmpEnrollmentSaveReq.class);
        empEnrollmentService.saveEmpEnrollments(empEnrollmentSaveReq, file);
        EmpEnrollmentGetReq empEnrollmentGetReq = new EmpEnrollmentGetReq();
        empEnrollmentGetReq.setEmpId(empEnrollmentSaveReq.getEmpRegisterDtlTO().getId());

        EmpEnrollmentResp resp = empEnrollmentService.getEmpEnrollments(empEnrollmentGetReq);
        populateOnLoadMap(resp);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private void populateOnLoadMap(EmpEnrollmentResp resp) {
        resp.setEmpRegisterDropDownTO(empRegisterService.getEmpRegisterDropDown());
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_EMP_SERVICE_HISTORY_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> getEmpServiceHistory(
            @RequestBody ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        return new ResponseEntity<EmpServiceHistoryResp>(getServiceHistoryResp(projEmpServiceHistoryReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_PROJ_EMP_LATEST_SERVICE_HISTORY, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> getEmpLatestServiceHistory(
            @RequestBody ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpLatestServiceHistory(projEmpServiceHistoryReq);
        return new ResponseEntity<EmpServiceHistoryResp>(resp, HttpStatus.OK);
    }

    private EmpServiceHistoryResp getServiceHistoryResp(ProjEmpServiceHistoryReq projEmpServiceHistoryReq) {
        EmpServiceHistoryResp resp = empEnrollmentService.getEmpServiceHistory(projEmpServiceHistoryReq);
        resp.setEmpServiceType(empRegisterService.getEmployeeServiceType());
        return resp;
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PROJ_EMP_SERVICE_HISTORY_REGISTERS, method = RequestMethod.POST)
    public ResponseEntity<EmpServiceHistoryResp> saveEmpServiceHistory(
            @RequestBody ProjEmpRegistersSaveReq projEmpRegistersSaveReq) {
        empEnrollmentService.saveEmpServiceHistory(projEmpRegistersSaveReq);
        ProjEmpServiceHistoryReq req = new ProjEmpServiceHistoryReq();
        req.setEmpId(projEmpRegistersSaveReq.getProjEmpRegisterTO().getEmpId());
        EmpServiceHistoryResp resp = getServiceHistoryResp(req);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<EmpServiceHistoryResp>(resp, HttpStatus.OK);
    }

    /*@GetMapping(value = RegisterURLConstants.DOWNLOAD_ENROLLMENT_CONTRACT)
    public ResponseEntity<ByteArrayResource> downloadEnrollmentContract(@RequestParam("enrollId") Long enrollId) {
        EmpEnrollmentDtlTO enrollmentDtlTO = empEnrollmentService.getEmpEnrollment(enrollId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(enrollmentDtlTO.getContractDocumentFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + enrollmentDtlTO.getContractDocumentFileName() + "\"")
                .body(new ByteArrayResource(enrollmentDtlTO.getContractDocument()));
    }*/
    
    @GetMapping(value = RegisterURLConstants.DOWNLOAD_ENROLLMENT_CONTRACT)
    public String[] downloadEnrollmentContract(@RequestParam("enrollId") Long enrollId) {
        EmpEnrollmentDtlTO enrollmentDtlTO = empEnrollmentService.getEmpEnrollment(enrollId);
        String[] file_info = { enrollmentDtlTO.getContractDocumentFileType(), enrollmentDtlTO.getContractDocumentFileName(), enrollmentDtlTO.getContractDocumentFileSize(), enrollmentDtlTO.getUploadFullPath() };
        return file_info;
    }

}
