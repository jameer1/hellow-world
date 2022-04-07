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
import com.rjtech.aws.common.s3.file.service.AswS3FileService;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;
import com.rjtech.register.fixedassets.req.AssetRepairsDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetRepairsResp;
import com.rjtech.register.service.fixedassets.FixedAssetsRepairsRecordsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class FixedAssetRepairsController {

    @Autowired
    private FixedAssetsRepairsRecordsService fixedAssetsRepairsRecordsService;

    @Autowired
    private AswS3FileService aswS3FileService;

    @RequestMapping(value = RegisterURLConstants.GET_REPAIRS_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> getSalesRecords(
            @RequestBody FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {

        FixedAssetRepairsResp resp = fixedAssetsRepairsRecordsService
                .getFixedAssetsRepairs(fixedAssetRepairsRecordsGetReq);
        return new ResponseEntity<FixedAssetRepairsResp>(resp, HttpStatus.OK);

    }

    @RequestMapping(value = RegisterURLConstants.SAVE_REPAIRS_RECORDS, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> saveFixedAssetsRepairs(MultipartFile file, String clientRegReq)
            throws IOException {
        FixedAssetRepairsRecordsSaveReq fixedAssetRepairsRecordsSaveReq = AppUtils.fromJson(clientRegReq,
                FixedAssetRepairsRecordsSaveReq.class);
        fixedAssetsRepairsRecordsService.saveFixedAssetsRepairs(file, fixedAssetRepairsRecordsSaveReq);
        FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq = new FixedAssetRepairsRecordsGetReq();
        fixedAssetRepairsRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetRepairsRecordsGetReq.setFixedAssetid(fixedAssetRepairsRecordsSaveReq.getFixedAssetid());
        FixedAssetRepairsResp resp = fixedAssetsRepairsRecordsService
                .getFixedAssetsRepairs(fixedAssetRepairsRecordsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<FixedAssetRepairsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.FIXED_ASSETS_REPAIRS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<FixedAssetRepairsResp> deactivateAssetsRepairs(
            @RequestBody AssetRepairsDeactiveReq assetRepairsDeactiveReq) {
        fixedAssetsRepairsRecordsService.deactivateAssetsRepairs(assetRepairsDeactiveReq);

        FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq = new FixedAssetRepairsRecordsGetReq();
        fixedAssetRepairsRecordsGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        fixedAssetRepairsRecordsGetReq.setFixedAssetid(assetRepairsDeactiveReq.getFixedAssetid());

        FixedAssetRepairsResp resp = fixedAssetsRepairsRecordsService
                .getFixedAssetsRepairs(fixedAssetRepairsRecordsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<FixedAssetRepairsResp>(resp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.FIXED_ASSETS_REPAIRS_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> repairsDownloadFile(@RequestParam("repairRecordId") Long repairRecordId)
            throws IOException {
        FixedAssetRepairsRecordsDtlTO fileTo = fixedAssetsRepairsRecordsService.getAssetRecordFileInfo(repairRecordId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getRepairsRecordsDetailsFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getRepairsRecordsDetailsFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getRepairsRecordDetails()));
    }
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_REPAIRS_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getProjRepairsDocuemnts(
            @RequestBody FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq) {
        DocumentsResp resp = fixedAssetsRepairsRecordsService
                .getProjRepairsDocuemnts(fixedAssetRepairsRecordsGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);

    }

}
