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
import com.rjtech.register.fixedassets.req.RevenueSalesDeactiveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesGetReq;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.fixedassets.resp.RevenueSalesResp;
import com.rjtech.register.service.fixedassets.RevenueSalesService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class RevenueSalesController {

    @Autowired
    private RevenueSalesService revenueSalesService;

    @RequestMapping(value = RegisterURLConstants.GET_REVENUE_SALES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> getRevenueSales(@RequestBody RevenueSalesGetReq revenueSalesGetReq) {
        RevenueSalesResp resp = revenueSalesService.getRevenueSales(revenueSalesGetReq);
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_REVENUE_SALES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> saveRevenueSales(@RequestBody RevenueSalesSaveReq revenueSalesSaveReq) {
        revenueSalesService.saveRevenueSales(revenueSalesSaveReq);

        RevenueSalesGetReq revenueSalesGetReq = new RevenueSalesGetReq();
        revenueSalesGetReq.setFixedAssetid(revenueSalesSaveReq.getFixedAssetid());
        revenueSalesGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        RevenueSalesResp resp = revenueSalesService.getRevenueSales(revenueSalesGetReq);

        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.REVENUE_SALES_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> deactivateRevenueSales(
            @RequestBody RevenueSalesDeactiveReq revenueSalesDeactiveReq) {
        revenueSalesService.deactivateRevenueSales(revenueSalesDeactiveReq);

        RevenueSalesGetReq revenueSalesGetReq = new RevenueSalesGetReq();
        revenueSalesGetReq.setFixedAssetid(revenueSalesDeactiveReq.getFixedAssetid());
        revenueSalesGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        RevenueSalesResp resp = revenueSalesService.getRevenueSales(revenueSalesGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.REVENUE_SALES_DELETE, method = RequestMethod.POST)
    public ResponseEntity<RevenueSalesResp> revenueSalesDelete(
            @RequestBody RevenueSalesDeactiveReq revenueSalesDeleteReq) {
        revenueSalesService.revenueSalesDelete(revenueSalesDeleteReq);

        RevenueSalesGetReq revenueSalesGetReq = new RevenueSalesGetReq();
        revenueSalesGetReq.setFixedAssetid(revenueSalesDeleteReq.getFixedAssetid());
        revenueSalesGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        RevenueSalesResp resp = revenueSalesService.getRevenueSales(revenueSalesGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<RevenueSalesResp>(resp, HttpStatus.OK);
    }

}
