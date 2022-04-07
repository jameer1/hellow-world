package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

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
import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;
import com.rjtech.register.fixedassets.req.AssetPurchaseDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;
import com.rjtech.register.service.fixedassets.FixedAssetsPurachaseRecordsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class FixedAssetPurchaseAcqulisitionController {

    @Autowired
    private FixedAssetsPurachaseRecordsService fixedAssetsPurachaseRecordsService;

    @PostMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_PURACHASE_ACQULISITION)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> getSalesRecords(
            @RequestBody FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {

        FixedAssetPurachseAcqulistionResp resp = fixedAssetsPurachaseRecordsService
                .getFixedAssetsPurachaseAcqulisition(fixedAssetPurachaseRecordsGetReq);
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(resp, HttpStatus.OK);

    }

    @PostMapping(value = RegisterURLConstants.SAVE_PURACHASE_ACQULISITION)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> saveFixedAssetsPurachaseAcqulisition(MultipartFile file,
            String clientRegReq) throws IOException {
        FixedAssetPurachaseRecordsSaveReq fixedAseetPurachaseAcqulisitionSaveReq = AppUtils.fromJson(clientRegReq,
                FixedAssetPurachaseRecordsSaveReq.class);

        fixedAssetsPurachaseRecordsService.saveFixedAssetsPurachaseAcqulisition(file,
                fixedAseetPurachaseAcqulisitionSaveReq);
        FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq = new FixedAssetPurachaseRecordsGetReq();
        fixedAssetPurachaseRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetPurachaseRecordsGetReq.setFixedAssetid(fixedAseetPurachaseAcqulisitionSaveReq.getFixedAssetid());
        FixedAssetPurachseAcqulistionResp resp = fixedAssetsPurachaseRecordsService
                .getFixedAssetsPurachaseAcqulisition(fixedAssetPurachaseRecordsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(resp, HttpStatus.OK);
    }

    @PostMapping(value = RegisterURLConstants.FIXED_ASSETS_PURCHASE_ACQULISITION_DELETE)
    public ResponseEntity<FixedAssetPurachseAcqulistionResp> deactivateAssetsPurchase(
            @RequestBody AssetPurchaseDeactiveReq purchaseDeactiveReq) {
        fixedAssetsPurachaseRecordsService.deactivateAssetsPurachase(purchaseDeactiveReq);

        FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq = new FixedAssetPurachaseRecordsGetReq();
        fixedAssetPurachaseRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetPurachaseRecordsGetReq.setFixedAssetid(purchaseDeactiveReq.getFixedAssetid());

        FixedAssetPurachseAcqulistionResp resp = fixedAssetsPurachaseRecordsService
                .getFixedAssetsPurachaseAcqulisition(fixedAssetPurachaseRecordsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<FixedAssetPurachseAcqulistionResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.FIXED_ASSETS_PURACHASE_ACQULISITION_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> purchaseAcqulistitonDownloadFile(
            @RequestParam("purchaseRecordId") Long purchaseRecordId) throws IOException {
        FixedAssetAqulisitionRecordsDtlTO fileTo = fixedAssetsPurachaseRecordsService
                .getAssetRecordFileInfo(purchaseRecordId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getPurchaseRecordsDetailsFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getPurchaseRecordsDetailsFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getPrachaseRecordDetails()));
    }

    @PostMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_PURACHASE_ACQULISITION_DOCUMENTS)
    public ResponseEntity<DocumentsResp> getProjPurchaseDocuemnts(
            @RequestBody FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq) {
        DocumentsResp resp = fixedAssetsPurachaseRecordsService
                .getProjPurchaseDocuemnts(fixedAssetPurachaseRecordsGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);
    }
}
