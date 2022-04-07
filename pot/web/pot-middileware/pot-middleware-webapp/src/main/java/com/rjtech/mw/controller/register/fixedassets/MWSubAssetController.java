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
import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWSubAssetController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_SUB_ASSETS, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> getSubAssets(@RequestBody SubAssetsGetReq subAssetsGetReq) {
        SubAssetsResp resp = mwFixedAssetsService.getSubAssets(subAssetsGetReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SUBASSETS, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> saveSubAssets(@RequestBody SubAssetsSaveReq subAssetsSaveReq) {
        SubAssetsResp resp = mwFixedAssetsService.saveSubAssets(subAssetsSaveReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SUB_ASSETS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> deactivateSubAsset(@RequestBody SubAssetDeleteReq subAssetsDeleteReq) {
        SubAssetsResp resp = mwFixedAssetsService.deactivateSubAsset(subAssetsDeleteReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SUB_ASSETS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> subAssetDelete(@RequestBody SubAssetDeleteReq subAssetsDeleteReq) {
        SubAssetsResp resp = mwFixedAssetsService.subAssetDelete(subAssetsDeleteReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> getAssetById(@RequestBody SubAssetsGetReq subAssetsGetReq) {
        SubAssetsResp resp = mwFixedAssetsService.getAssetById(subAssetsGetReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

}
