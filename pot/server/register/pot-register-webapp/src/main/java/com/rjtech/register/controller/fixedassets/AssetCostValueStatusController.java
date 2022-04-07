package com.rjtech.register.controller.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusSaveReq;
import com.rjtech.register.fixedassets.resp.AssetCostValueStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.service.fixedassets.AssetCostValueStatusService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class AssetCostValueStatusController {

    @Autowired
    private AssetCostValueStatusService assetCostValueStatusService;

    @PostMapping(value = RegisterURLConstants.GET_ASSET_COST_VALUE_STATUS)
    public ResponseEntity<AssetCostValueStatusResp> getAssetCostValueStatus(
            @RequestBody AssetCostValueStatusGetReq assetCostValueStatusGetReq) {
        AssetCostValueStatusResp resp = assetCostValueStatusService.getAssetCostValueStatus(assetCostValueStatusGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @PostMapping(value = RegisterURLConstants.SAVE_ASSET_COST_VALUE_STATUS)
    public ResponseEntity<AssetCostValueStatusResp> saveAssetCostValueStatus(MultipartFile file, String clientRegReq) {
        AssetCostValueStatusSaveReq assetCostValueStatusSaveReq = AppUtils.fromJson(clientRegReq,
                AssetCostValueStatusSaveReq.class);
        assetCostValueStatusService.saveAssetCostValueStatus(file, assetCostValueStatusSaveReq);
        AssetCostValueStatusGetReq assetCostValueStatusGetReq = new AssetCostValueStatusGetReq();
        assetCostValueStatusGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        assetCostValueStatusGetReq.setFixedAssetid(assetCostValueStatusGetReq.getFixedAssetid());
        AssetCostValueStatusResp resp = assetCostValueStatusService.getAssetCostValueStatus(assetCostValueStatusGetReq);

        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.ASSET_COST_VALUE_STATUS_DEACTIVATES)
    public ResponseEntity<AssetCostValueStatusResp> deactivateAssetCostValueStatus(
            @RequestBody AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq) {
        assetCostValueStatusService.deactivateAssetCostValueStatus(assetCostValueStatusDeactiveReq);

        AssetCostValueStatusGetReq assetCostValueStatusGetReq = new AssetCostValueStatusGetReq();
        assetCostValueStatusGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        assetCostValueStatusGetReq.setFixedAssetid(assetCostValueStatusGetReq.getFixedAssetid());

        AssetCostValueStatusResp resp = assetCostValueStatusService.getAssetCostValueStatus(assetCostValueStatusGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.ASSET_COST_VALUE_STATUS_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> assetAssetCostValueStatusDocDownloadFile(
            @RequestParam("assetCostValueStatusId") Long assetCostValueStatusId) {
        AssetCostValueStatusDtlTO fileTo = assetCostValueStatusService
                .assetCostValueStatusDocDownloadFile(assetCostValueStatusId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getAssetCostValueDocumentFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getAssetCostValueDocumentFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getAssetCostValueRecords()));
    }

    @PostMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_ASSET_COST_DOCUMENTS)
    public ResponseEntity<DocumentsResp> getProjAssetCostValueDocuemnts(
            @RequestBody AssetCostValueStatusGetReq assetCostValueStatusGetReq) {

        DocumentsResp resp = assetCostValueStatusService.getProjAssetCostValueDocuemnts(assetCostValueStatusGetReq);
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

}
