package com.rjtech.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.finance.constants.FinanceURLConstants;
import com.rjtech.finance.resp.FinanceMapResp;
import com.rjtech.finance.service.FinanceMasterMapService;

@RestController
@RequestMapping(FinanceURLConstants.PARH_URL)
public class FinanceMasterMapController {

    @Autowired
    private FinanceMasterMapService financeMasterMapService;

    @RequestMapping(value = FinanceURLConstants.GET_TAXCODES_MAP, method = RequestMethod.POST)
    public ResponseEntity<FinanceMapResp> taxCodeMap(@RequestBody UserReq userReq) {

        return new ResponseEntity<FinanceMapResp>(financeMasterMapService.taxCodeMap(userReq), HttpStatus.OK);
    }

}
