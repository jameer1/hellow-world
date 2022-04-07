package com.rjtech.register.controller.fixedassets;

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
import com.rjtech.register.fixedassets.dto.RentalValueDtlTO;
import com.rjtech.register.fixedassets.req.RentalValueDeactiveReq;
import com.rjtech.register.fixedassets.req.RentalValueGetReq;
import com.rjtech.register.fixedassets.req.RentalValueSaveReq;
import com.rjtech.register.fixedassets.resp.RentalValueResp;
import com.rjtech.register.service.fixedassets.RentalValueService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class AssetRentalValueController {

    @Autowired
    private RentalValueService rentalValueService;

    @RequestMapping(value = RegisterURLConstants.GET_RENTALVALUE, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> getRentalValue(@RequestBody RentalValueGetReq rentalValueGetReq) {
        RentalValueResp resp = rentalValueService.getRentalValue(rentalValueGetReq);
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_RENTALVALUE, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> saveRentalValue(@RequestBody RentalValueSaveReq rentalValueSaveReq) {

        rentalValueService.saveRentalValue(rentalValueSaveReq);

        RentalValueGetReq rentalValueGetReq = new RentalValueGetReq();
        rentalValueGetReq.setFixedAssetid(rentalValueSaveReq.getFixedAssetid());
        rentalValueGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        RentalValueResp resp = new RentalValueResp();
        rentalValueService.getRentalValue(rentalValueGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.RENATAL_VALUE_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> deactivateRentalValue(
            @RequestBody RentalValueDeactiveReq rentalValueDeactiveReq) {
        rentalValueService.deactivateRentalValue(rentalValueDeactiveReq);

        RentalValueGetReq rentalValueGetReq = new RentalValueGetReq();
        rentalValueGetReq.setFixedAssetid(rentalValueDeactiveReq.getFixedAssetid());
        rentalValueGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        RentalValueResp resp = rentalValueService.getRentalValue(rentalValueGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.RENATAL_VALUE_DELETE, method = RequestMethod.POST)
    public ResponseEntity<RentalValueResp> rentalValueDelete(@RequestBody RentalValueDeactiveReq rentalValueDeleteReq) {
        rentalValueService.rentalValueDelete(rentalValueDeleteReq);

        RentalValueGetReq rentalValueGetReq = new RentalValueGetReq();
        rentalValueGetReq.setFixedAssetid(rentalValueDeleteReq.getFixedAssetid());
        rentalValueGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        RentalValueResp resp = rentalValueService.getRentalValue(rentalValueGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<RentalValueResp>(resp, HttpStatus.OK);
    }

}
