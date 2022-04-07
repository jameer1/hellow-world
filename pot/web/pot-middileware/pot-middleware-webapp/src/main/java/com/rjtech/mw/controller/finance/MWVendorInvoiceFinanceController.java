package com.rjtech.mw.controller.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.finance.constants.FinanceURLConstants;
import com.rjtech.finance.req.EmployeeTypeSaveReq;
import com.rjtech.finance.resp.EmpPayrollTypeResp;
import com.rjtech.mw.service.centlib.MWCentralLibService;
import com.rjtech.mw.service.common.MWCommonService;
import com.rjtech.mw.service.finance.MWFinanceMasterService;
import com.rjtech.mw.service.projlib.MWProjLibService;

//@RestController
//@RequestMapping(FinanceURLConstants.PARH_URL)
public class MWVendorInvoiceFinanceController {
	
	@Autowired
    private MWFinanceMasterService mwFinanceMasterService;

    @Autowired
    private MWCentralLibService mwCentralLiblService;

    @Autowired
    private MWProjLibService mwProjLibService;

    @Autowired
    private MWCommonService mwCommonService;
    
    
    @RequestMapping(value = FinanceURLConstants.SAVE_VENDOR_POST_INVOICE, method = RequestMethod.POST)
    public ResponseEntity<EmpPayrollTypeResp> saveEmpPayTypeCycle(
            @RequestBody EmployeeTypeSaveReq employeeTypeSaveReq) {
    	
        return new ResponseEntity<EmpPayrollTypeResp>(mwFinanceMasterService.saveEmpPayTypeCycle(employeeTypeSaveReq),
                HttpStatus.OK);

    }

}
