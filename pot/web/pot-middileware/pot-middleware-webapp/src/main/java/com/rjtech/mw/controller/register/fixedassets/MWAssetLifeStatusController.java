package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusGetReq;
import com.rjtech.register.fixedassets.resp.AssetLifeStatusResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWAssetLifeStatusController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_LIFE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> getAssetLifeStatus(
            @RequestBody AssetLifeStatusGetReq assetLifeStatusGetReq) {
        AssetLifeStatusResp resp = mwFixedAssetsService.getAssetLifeStatus(assetLifeStatusGetReq);
        return new ResponseEntity<AssetLifeStatusResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_ASSET_LIFE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> saveAssetLifeStatus(MultipartFile file, String assetsLifeStatusSaveReq) {
        return new ResponseEntity<AssetLifeStatusResp>(
                mwFixedAssetsService.saveAssetLifeStatus(file, assetsLifeStatusSaveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> deactivateAssetLifeStatus(
            @RequestBody AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq) {
        return new ResponseEntity<AssetLifeStatusResp>(
                mwFixedAssetsService.deactivateAssetLifeStatus(assetLifeStatusDeactiveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> assetLifeStatusDelete(
            @RequestBody AssetLifeStatusDeleteReq assetLifeStatusDeleteReq) {
        return new ResponseEntity<AssetLifeStatusResp>(
                mwFixedAssetsService.assetLifeStatusDelete(assetLifeStatusDeleteReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> assetLifeStatusDocDownloadFile(
            @RequestParam("assetLifeStatusId") Long assetLifeStatusId) {
        return mwFixedAssetsService.assetLifeStatusDocDownloadFile(assetLifeStatusId);

    }

}
