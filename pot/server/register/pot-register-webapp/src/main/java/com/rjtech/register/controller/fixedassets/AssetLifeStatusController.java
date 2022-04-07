package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusSaveReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.LongTermLeasingGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.resp.AssetLifeStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;
import com.rjtech.register.fixedassets.resp.LongTermLeasingResp;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;
import com.rjtech.register.service.fixedassets.AssetLifeStatusService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class AssetLifeStatusController {

    @Autowired
    private AssetLifeStatusService assetLifeStatusService;

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_LIFE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> getAssetLifeStatus(
            @RequestBody AssetLifeStatusGetReq assetLifeStatusGetReq) {
        AssetLifeStatusResp resp = assetLifeStatusService.getAssetLifeStatus(assetLifeStatusGetReq);
        return new ResponseEntity<AssetLifeStatusResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.SAVE_ASSET_LIFE_STATUS, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> saveAssetLifeStatus(MultipartFile file, String clientRegReq)
            throws IOException {
        AssetLifeStatusSaveReq assetLifeStatusSaveReq = AppUtils.fromJson(clientRegReq, AssetLifeStatusSaveReq.class);

        assetLifeStatusService.saveAssetLifeStatus(file, assetLifeStatusSaveReq);

        AssetLifeStatusGetReq assetLifeStatusGetReq = new AssetLifeStatusGetReq();
        assetLifeStatusGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        assetLifeStatusGetReq.setFixedAssetid(assetLifeStatusGetReq.getFixedAssetid());

        AssetLifeStatusResp resp = assetLifeStatusService.getAssetLifeStatus(assetLifeStatusGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<AssetLifeStatusResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> assetLifeStatusDelete(
            @RequestBody AssetLifeStatusDeleteReq assetLifeStatusDeleteReq) {
        assetLifeStatusService.assetLifeStatusDelete(assetLifeStatusDeleteReq);

        AssetLifeStatusGetReq assetLifeStatusGetReq = new AssetLifeStatusGetReq();

        assetLifeStatusGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        AssetLifeStatusResp resp = assetLifeStatusService.getAssetLifeStatus(assetLifeStatusGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<AssetLifeStatusResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<AssetLifeStatusResp> deactivateAssetLifeStatus(
            @RequestBody AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq) {
        assetLifeStatusService.deactivateAssetLifeStatus(assetLifeStatusDeactiveReq);

        AssetLifeStatusGetReq assetLifeStatusGetReq = new AssetLifeStatusGetReq();
        assetLifeStatusGetReq.setFixedAssetid(assetLifeStatusDeactiveReq.getFixedAssetid());
        assetLifeStatusGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        AssetLifeStatusResp resp = assetLifeStatusService.getAssetLifeStatus(assetLifeStatusGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<AssetLifeStatusResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.ASSET_LIFE_STATUS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> assetLifeStatusDocDownloadFile(
            @RequestParam("assetLifeStatusId") Long assetLifeStatusId) {
        AssetLifeStatusDtlTO fileTo = assetLifeStatusService.assetLifeStatusDocDownloadFile(assetLifeStatusId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileTo.getLifeAssignmentRecordsDocumentFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getLifeAssignmentRecordsDocumentFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getLifeAssignmentRecords()));
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_ASSET_LIFE_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getProjAssetLifeStatusDocuemnts(
            @RequestBody AssetLifeStatusGetReq assetLifeStatusGetReq) {

        DocumentsResp resp = assetLifeStatusService
                .getProjAssetLifeStatusDocuemnts(assetLifeStatusGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);

    }

}
