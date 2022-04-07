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
import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsSaveReq;
import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWMortgagePaymentController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_MORTGAGEE_PAYMENTS, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> getMortgageePayments(
            @RequestBody MortgageePaymentsGetReq mortgageePaymentsGetReq) {
        MortgagePaymentResp resp = mwFixedAssetsService.getMortgageePayments(mortgageePaymentsGetReq);
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MORTGAGEE_PAYMENTS, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> saveMortgageePayments(
            @RequestBody MortgageePaymentsSaveReq mortgageePaymentsSaveReq) {

        MortgagePaymentResp resp = mwFixedAssetsService.saveMortgageePayments(mortgageePaymentsSaveReq);
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.MORTGAGEE_PAYMENTS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> deactivateMortgageePayments(
            @RequestBody MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq) {
        MortgagePaymentResp resp = mwFixedAssetsService.deactivateMortgageePayments(mortgageePaymentsDeactiveReq);
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

}
