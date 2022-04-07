package com.rjtech.register.controller.fixedassets;

import java.util.List;

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
import com.rjtech.register.fixedassets.dto.MortgagePaymentDtlTO;
import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsSaveReq;
import com.rjtech.register.fixedassets.req.SalesRecordsGetReq;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;
import com.rjtech.register.service.fixedassets.MortgagePaymentService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MortgagePaymentController {

    @Autowired
    private MortgagePaymentService mortgagePaymentService;

    @RequestMapping(value = RegisterURLConstants.GET_MORTGAGEE_PAYMENTS, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> getMortgageePayments(
            @RequestBody MortgageePaymentsGetReq mortgageePaymentsGetReq) {
        MortgagePaymentResp resp = mortgagePaymentService.getMortgageePayments(mortgageePaymentsGetReq);
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_MORTGAGEE_PAYMENTS, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> saveMortgageePayments(
            @RequestBody MortgageePaymentsSaveReq mortgageePaymentsSaveReq) {

        mortgagePaymentService.saveMortgageePayments(mortgageePaymentsSaveReq);

        MortgageePaymentsGetReq mortgageePaymentsGetReq = new MortgageePaymentsGetReq();
        mortgageePaymentsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        mortgageePaymentsGetReq.setFixedAssetid(mortgageePaymentsGetReq.getFixedAssetid());

        MortgagePaymentResp resp = mortgagePaymentService.getMortgageePayments(mortgageePaymentsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.MORTGAGEE_PAYMENTS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> deactivateMortgageePayments(
            @RequestBody MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq) {
        mortgagePaymentService.deactivateMortgageePayments(mortgageePaymentsDeactiveReq);

        MortgageePaymentsGetReq mortgageePaymentsGetReq = new MortgageePaymentsGetReq();
        mortgageePaymentsGetReq.setFixedAssetid(mortgageePaymentsDeactiveReq.getFixedAssetid());
        mortgageePaymentsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        MortgagePaymentResp resp = mortgagePaymentService.getMortgageePayments(mortgageePaymentsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.MORTGAGEE_PAYMENTS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<MortgagePaymentResp> mortgageePaymentsDelete(
            @RequestBody MortgageePaymentsDeactiveReq mortgageePaymentsDeleteReq) {
        mortgagePaymentService.mortgageePaymentsDelete(mortgageePaymentsDeleteReq);

        MortgageePaymentsGetReq mortgageePaymentsGetReq = new MortgageePaymentsGetReq();
        mortgageePaymentsGetReq.setFixedAssetid(mortgageePaymentsDeleteReq.getFixedAssetid());
        mortgageePaymentsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        MortgagePaymentResp resp = mortgagePaymentService.getMortgageePayments(mortgageePaymentsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<MortgagePaymentResp>(resp, HttpStatus.OK);
    }

}
