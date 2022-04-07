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
import com.rjtech.register.fixedassets.req.AssetPurchaseDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWFixedAssetPurchaseAcqulisitionController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_PURACHASE_ACQULISITION, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> getFixedAssetsPurachaseAcqulisition(
            @RequestBody FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(
                mwFixedAssetsService.getFixedAssetsPurachaseAcqulisition(fixedAssetPurachaseRecordsGetReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_PURACHASE_ACQULISITION, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> saveFixedAssetPurachaseAcqulistion(
            @RequestBody MultipartFile file, String fixedAssetPurchaseAcqulisitionSaveReqStr) {
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(
                mwFixedAssetsService.saveFixedAssetPurachaseAcqulistion(file, fixedAssetPurchaseAcqulisitionSaveReqStr),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_PURCHASE_ACQULISITION_DELETE, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> deactivateAssetsPurachase(
            @RequestBody AssetPurchaseDeactiveReq purchaseDeactiveReq) {
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(
                mwFixedAssetsService.deactivateAssetsPurachase(purchaseDeactiveReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.FIXED_ASSETS_PURACHASE_ACQULISITION_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> purchaseAcqulistitonDownloadFile(
            @RequestParam("purchaseRecordId") Long purchaseRecordId) {
        return mwFixedAssetsService.getAssetPurchaseRecordFileInfo(purchaseRecordId);
    }

}
