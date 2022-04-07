package com.rjtech.register.controller.emp;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.service.emp.EmpDocsCommonService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class EmpCommonController {

    @Autowired
    private EmpDocsCommonService empDocsCommonService;

    /*
     * @RequestMapping(value = ProcurementURLConstants.SAVE_PRE_CONTRACT_DOCS, method = RequestMethod.POST)
    public ResponseEntity<PreContractDocResp> savePreContranctDocs(MultipartFile[] files,
            String precontractDocSaveReqStr) throws IOException {
    	System.out.println("savePreContranctDocs function of ProcurementController precontractDocSaveReqStr string:"+precontractDocSaveReqStr);
        PrecontractDocSaveReq precontractDocSaveReq = AppUtils.fromJson(precontractDocSaveReqStr,
                PrecontractDocSaveReq.class);
        System.out.println("result:"+precontractDocSaveReq);
        procurementService.savePreContranctDocs(files, precontractDocSaveReq);
        PreContractDocResp preContractDocResp = new PreContractDocResp();
        if (CommonUtil.isListHasData(precontractDocSaveReq.getPreContractDocsTOs())) {
            ProcurementGetReq procurementGetReq = new ProcurementGetReq();
            procurementGetReq.setContractId(precontractDocSaveReq.getPreContractDocsTOs().get(0).getPreContractId());
            procurementGetReq.setStatus(StatusCodes.ACTIVE.getValue());
            preContractDocResp = procurementService.getPreContractDocs(procurementGetReq);
        }
        preContractDocResp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<PreContractDocResp>(preContractDocResp, HttpStatus.OK);
    }
     */
    @RequestMapping(value = RegisterURLConstants.SAVE_EMPLOYEE_DOCS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterResp> saveEmployeeDocs(MultipartFile[] files,
            String employeeDocsStr) throws IOException {
    	System.out.println("saveEmployeeDocs function of EmpCommonController string:"+employeeDocsStr);
    	EmpRegisterReq empRegisterReq = AppUtils.fromJson(employeeDocsStr,
    			EmpRegisterReq.class);
    	empDocsCommonService.saveEmployeeDocs( files, empRegisterReq );
    	EmpRegisterResp empRegisterResp = new EmpRegisterResp();
    	empRegisterResp.setProjectId( empRegisterReq.getProjId() );
    	return new ResponseEntity<EmpRegisterResp>( empRegisterResp, HttpStatus.OK );
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_EMPLOYEE_DOCS, method = RequestMethod.POST)
    public ResponseEntity<EmpRegisterResp> getEmployeeDocs( @RequestBody EmpRegisterReq empRegisterReq ) {
    	return new ResponseEntity<EmpRegisterResp>( empDocsCommonService.getEmployeeDocs( empRegisterReq ), HttpStatus.OK );
    }
}
