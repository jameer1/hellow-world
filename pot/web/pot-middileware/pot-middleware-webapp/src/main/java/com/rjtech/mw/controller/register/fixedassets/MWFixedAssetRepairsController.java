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
import com.rjtech.register.fixedassets.req.AssetRepairsDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsGetReq;
import com.rjtech.register.fixedassets.resp.FixedAssetRepairsResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWFixedAssetRepairsController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_REPAIRS_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> getFixedAssetsRepairs(
            @RequestBody FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {
        return new ResponseEntity<FixedAssetRepairsResp>(
                mwFixedAssetsService.getFixedAssetsRepairs(fixedAssetRepairsRecordsGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_REPAIRS_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> saveFixedAssetRepairs(@RequestBody MultipartFile file,
            String fixedAssetRepairsSaveReqStr) {
        return new ResponseEntity<FixedAssetRepairsResp>(
                mwFixedAssetsService.saveFixedAssetRepairs(file, fixedAssetRepairsSaveReqStr), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REPAIRS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> deactivateAssetsRepairs(
            @RequestBody AssetRepairsDeactiveReq assetRepairsDeactiveReq) {
        return new ResponseEntity<FixedAssetRepairsResp>(
                mwFixedAssetsService.deactivateAssetsRepairs(assetRepairsDeactiveReq), HttpStatus.OK);
    }

     @GetMapping(value = RegisterURLConstants.FIXED_ASSETS_REPAIRS_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> repairsDownloadFile(
            @RequestParam("repairRecordId") Long repairRecordId) {
        return mwFixedAssetsService.getAssetRepairRecordFileInfo(repairRecordId);
    }

}
