package com.rjtech.mw.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.resp.CommonMapResp;
import com.rjtech.mw.service.common.MWCommonMapService;

@RestController
@RequestMapping(CommonConstants.PARH_URL)
public class MWCommonMapController {

    @Autowired
    private MWCommonMapService mwCommonMapService;

    @RequestMapping(value = CommonConstants.GET_COUNTRY_CODE_MAP, method = RequestMethod.POST)
    public ResponseEntity<CommonMapResp> countryCodeMap(@RequestBody UserReq userReq) {
        return new ResponseEntity<CommonMapResp>(mwCommonMapService.countryCodeMap(userReq), HttpStatus.OK);
    }

}
