package com.rjtech.projectlib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.projectlib.constans.ProjLibURLConstants;
import com.rjtech.projectlib.req.ChangeOrderReq;
import com.rjtech.projectlib.resp.ChangeOrderResp;
import com.rjtech.projectlib.service.ChangeOrderDtlService;

@RestController
@RequestMapping(ProjLibURLConstants.PARH_URL)
public class ChangeOrderDtlController {
	
	
	@Autowired
    ChangeOrderDtlService changeOrderDtlService;
	
	

    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveChangeOrderDetails( @RequestBody ChangeOrderReq ChangeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveChangeOrderDetails( ChangeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_CO_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> getChangeOrderDetails( @RequestBody ChangeOrderReq ChangeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.getChangeOrderDetails( ChangeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_SOW, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoScopeOfWork( @RequestBody ChangeOrderReq ChangeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveCoScopeOfWork( ChangeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_MANPOWER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoManpowerDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveCoManpowerDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.GET_CO_DETAILS_BY_CO_ID, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> getChangeOrderDetailsByCoId( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.getChangeOrderDetailsByCoId( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_PLANT_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoPlantDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveCoPlantDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_MATERIAL_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoMaterialDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveCoMaterialDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    @RequestMapping(value = ProjLibURLConstants.SAVE_CO_COST_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> saveCoCostDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.saveCoCostDetails( changeOrderReq ), HttpStatus.OK );
    }
    
    
    @RequestMapping(value = ProjLibURLConstants.UPDATE_CO_APPROVER_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<ChangeOrderResp> updateCoApproverDetails( @RequestBody ChangeOrderReq changeOrderReq ) {
        return new ResponseEntity<ChangeOrderResp>( changeOrderDtlService.updateCoApproverDetails( changeOrderReq ), HttpStatus.OK );
    }
}
