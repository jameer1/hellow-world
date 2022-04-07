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
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
import com.rjtech.register.fixedassets.resp.AssetCostValueStatusResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWAssetCostValueStatusController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_COST_VALUE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetCostValueStatusResp> getAssetCostValueStatus(
            @RequestBody AssetCostValueStatusGetReq assetCostValueStatusGetReq) {
        AssetCostValueStatusResp resp = mwFixedAssetsService.getAssetCostValueStatus(assetCostValueStatusGetReq);
        return new ResponseEntity<AssetCostValueStatusResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_ASSET_COST_VALUE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetCostValueStatusResp> saveAssetCostValueStatus(MultipartFile file,
            String assetCostValueStatusSaveReq) {
        return new ResponseEntity<AssetCostValueStatusResp>(
                mwFixedAssetsService.saveAssetCostValueStatus(file,assetCostValueStatusSaveReq), HttpStatus.OK);
    }
    
    @RequestMapping(value = RegisterURLConstants.ASSET_COST_VALUE_STATUS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<AssetCostValueStatusResp> deactivateAssetCostValueStatus(
            @RequestBody AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq) {
        return new ResponseEntity<AssetCostValueStatusResp>(
                mwFixedAssetsService.deactivateAssetCostValueStatus(assetCostValueStatusDeactiveReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.ASSET_COST_VALUE_STATUS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<AssetCostValueStatusResp> assetCostValueStatusDelete(
            @RequestBody AssetCostValueStatusDeleteReq assetCostValueStatusDeleteReq) {
        return new ResponseEntity<AssetCostValueStatusResp>(
                mwFixedAssetsService.assetCostValueStatusDelete(assetCostValueStatusDeleteReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.ASSET_COST_VALUE_STATUS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> assetCostValueStatusDocDownloadFile(@RequestParam("assetCostValueStatusId") Long assetCostValueStatusId) {
        return mwFixedAssetsService.assetCostValueStatusDocDownloadFile(assetCostValueStatusId);
        

    }

}
