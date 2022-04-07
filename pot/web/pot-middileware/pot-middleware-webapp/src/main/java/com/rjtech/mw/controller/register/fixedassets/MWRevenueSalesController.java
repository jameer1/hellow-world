package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.RevenueSalesDeactiveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesGetReq;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.fixedassets.resp.RevenueSalesResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWRevenueSalesController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_REVENUE_SALES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> getRevenueSales(@RequestBody RevenueSalesGetReq revenueSalesGetReq) {
        RevenueSalesResp resp = mwFixedAssetsService.getRevenueSales(revenueSalesGetReq);
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_REVENUE_SALES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> saveRevenueSales(@RequestBody RevenueSalesSaveReq revenueSalesSaveReq) {
        RevenueSalesResp resp = mwFixedAssetsService.saveRevenueSales(revenueSalesSaveReq);
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.REVENUE_SALES_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> deactivateRevenueSales(
            @RequestBody RevenueSalesDeactiveReq revenueSalesDeactiveReq) {
        RevenueSalesResp resp = mwFixedAssetsService.deactivateRevenueSales(revenueSalesDeactiveReq);
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

}
