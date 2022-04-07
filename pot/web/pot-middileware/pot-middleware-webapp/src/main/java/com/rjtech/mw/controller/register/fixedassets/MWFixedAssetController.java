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
import com.rjtech.register.fixedassets.req.FixedAssetRegDeactivateReq;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWFixedAssetController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    // 1.Immovable Assets List

    @RequestMapping(value = RegisterURLConstants.SAVE_FIXED_ASSETS_REGISTERS, method = RequestMethod.POST, produces = "application/json")

    public ResponseEntity<FixedAssetsRegisterOnLoadResp> saveFixedAssetsRegister(
            @RequestBody FixedAssetsSaveReq fixedAssetsSaveReq) {
        FixedAssetsRegisterOnLoadResp resp = mwFixedAssetsService.saveFixedAssetRegisters(fixedAssetsSaveReq);
        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REGISTERS_DEACTIVATE, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> fixedAssetRegistersDeactivate(
            @RequestBody FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq) {

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(
                mwFixedAssetsService.fixedAssetRegistersDeactivate(fixedAssetRegDeactivateReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REGISTERS_ONLOAD, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> fixedAssetsRegistersOnLoad(
            @RequestBody FixedAssetsGetReq fixedAssetsGetReq) {
        FixedAssetsRegisterOnLoadResp resp = mwFixedAssetsService.fixedAssetsRegisterOnLoad(fixedAssetsGetReq);

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_ONLY, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> getAssetOnly(
            @RequestBody FixedAssetsGetReq fixedAssetsGetReq) {
        FixedAssetsRegisterOnLoadResp resp = mwFixedAssetsService.getAssetOnly(fixedAssetsGetReq);
        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_SUB_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> getAssetSubsById(
            @RequestBody FixedAssetsGetReq fixedAssetsGetReq) {

        FixedAssetsRegisterOnLoadResp resp = mwFixedAssetsService.getAssetSubsById(fixedAssetsGetReq);

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SUB_ASSET, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetsRegisterOnLoadResp> saveSubAsset(
            @RequestBody FixedAssetsSaveReq fixedAssetsSaveReq) {

        FixedAssetsRegisterOnLoadResp resp = mwFixedAssetsService.saveSubAsset(fixedAssetsSaveReq);

        return new ResponseEntity<FixedAssetsRegisterOnLoadResp>(resp, HttpStatus.OK);

    }

}
