package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;
import com.rjtech.register.fixedassets.req.AssetRepairsDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetRepairsRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetRepairsResp;

public interface FixedAssetsRepairsRecordsService {

   public FixedAssetRepairsResp getFixedAssetsRepairs(FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq);

   public void saveFixedAssetsRepairs(MultipartFile file, FixedAssetRepairsRecordsSaveReq fixedAssetRepairsRecordsSaveReq);

   public void deactivateAssetsRepairs(AssetRepairsDeactiveReq assetRepairsDeactiveReq);

   public FixedAssetRepairsRecordsDtlTO getAssetRecordFileInfo(Long purchaseRecordId);
   
   DocumentsResp getProjRepairsDocuemnts(
           FixedAssetRepairsRecordsGetReq fixedAssetRepairsRecordsGetReq);
}
