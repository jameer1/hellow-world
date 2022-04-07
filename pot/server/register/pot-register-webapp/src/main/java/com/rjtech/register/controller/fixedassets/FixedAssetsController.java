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
import com.rjtech.register.fixedassets.req.FixedAssetLifeStatusOnLoadReq;
import com.rjtech.register.fixedassets.req.FixedAssetRegDeactivateReq;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;
import com.rjtech.register.service.fixedassets.FixedAssetsDetailsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class FixedAssetsController {

    @Autowired
    private FixedAssetsDetailsService fixedAssetsService;


    private FixedAssetsRegisterOnLoadResp populateFixedAssetsRegisterOnLoad(
            FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp, FixedAssetsGetReq fixedAssetsGetReq) {
        FixedAssetsRegisterOnLoadResp fixedAssetsDtlResp = new FixedAssetsRegisterOnLoadResp();

        fixedAssetsDtlResp.getFixedAssetDtlTOs()
                .addAll(fixedAssetsService.getFixedAssetRegister(fixedAssetsGetReq).getFixedAssetDtlTOs());
        return fixedAssetsDtlResp;
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REGISTERS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> fixedAssetsRegistersOnLoad(
            @RequestBody FixedAssetsGetReq fixedAssetsGetReq) {
        FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp = new FixedAssetsRegisterOnLoadResp();
        fixedAssetsRegisterOnLoadResp = populateFixedAssetsRegisterOnLoad(fixedAssetsRegisterOnLoadResp,
                fixedAssetsGetReq);
        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(fixedAssetsRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> fixedAssetRegistersDeactivate(
            @RequestBody FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq) {

        FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp = new FixedAssetsRegisterOnLoadResp();
        fixedAssetsService.fixedAssetRegistersDeactivate(fixedAssetRegDeactivateReq);
        FixedAssetsGetReq fixedAssetsGetReq = new FixedAssetsGetReq();
        fixedAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetsRegisterOnLoadResp = populateFixedAssetsRegisterOnLoad(fixedAssetsRegisterOnLoadResp,
                fixedAssetsGetReq);

        fixedAssetsRegisterOnLoadResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(fixedAssetsRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSET_LIFE_STATUS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> fixedAssetLifeStatusOnLoad(
            @RequestBody FixedAssetLifeStatusOnLoadReq fixedAssetLifeStatusOnLoadReq) {

        FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp = new FixedAssetsRegisterOnLoadResp();
        FixedAssetsGetReq fixedAssetsGetReq = new FixedAssetsGetReq();
        fixedAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetsRegisterOnLoadResp = populateFixedAssetsRegisterOnLoad(fixedAssetsRegisterOnLoadResp,
                fixedAssetsGetReq);

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(fixedAssetsRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_SUB_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> getAssetSubsById(
            @RequestBody FixedAssetsGetReq fixedAssetsGetReq) {

        FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp = new FixedAssetsRegisterOnLoadResp();

        fixedAssetsRegisterOnLoadResp.getFixedAssetDtlTOs()
                .addAll(fixedAssetsService.getAssetSubById(fixedAssetsGetReq).getFixedAssetDtlTOs());
        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(fixedAssetsRegisterOnLoadResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SUB_ASSET, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> saveSubAsset(
            @RequestBody FixedAssetsSaveReq fixedAssetsSaveReq) {
        fixedAssetsService.saveSubAsset(fixedAssetsSaveReq);

        FixedAssetsGetReq fixedAssetsGetReq = new FixedAssetsGetReq();
        fixedAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        FixedAssetsRegisterOnLoadResp fixedAssetsRegisterOnLoadResp = new FixedAssetsRegisterOnLoadResp();

        fixedAssetsRegisterOnLoadResp = populateFixedAssetsRegisterOnLoad(fixedAssetsRegisterOnLoadResp,
                fixedAssetsGetReq);

        fixedAssetsRegisterOnLoadResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(fixedAssetsRegisterOnLoadResp, HttpStatus.OK);
    }

}
