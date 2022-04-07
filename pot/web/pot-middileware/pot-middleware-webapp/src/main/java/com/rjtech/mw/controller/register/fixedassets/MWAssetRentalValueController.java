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
import com.rjtech.register.fixedassets.req.RentalValueDeactiveReq;
import com.rjtech.register.fixedassets.req.RentalValueGetReq;
import com.rjtech.register.fixedassets.req.RentalValueSaveReq;
import com.rjtech.register.fixedassets.resp.RentalValueResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWAssetRentalValueController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_RENTALVALUE, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> getRentalValue(@RequestBody RentalValueGetReq rentalValueGetReq) {
        RentalValueResp resp = mwFixedAssetsService.getRentalValue(rentalValueGetReq);
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_RENTALVALUE, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> saveRentalValue(@RequestBody RentalValueSaveReq rentalValueSaveReq) {
        RentalValueResp resp = mwFixedAssetsService.saveRentalValue(rentalValueSaveReq);
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.RENATAL_VALUE_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> deactivateRentalValue(
            @RequestBody RentalValueDeactiveReq rentalValueDeactiveReq) {
        return new ResponseEntity<RentalValueResp>(mwFixedAssetsService.deactivateRentalValue(rentalValueDeactiveReq),
                HttpStatus.OK);
    }

}
