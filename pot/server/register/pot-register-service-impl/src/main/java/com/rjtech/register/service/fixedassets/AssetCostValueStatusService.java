package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetCostValueStatusSaveReq;
import com.rjtech.register.fixedassets.resp.AssetCostValueStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;

public interface AssetCostValueStatusService {

    AssetCostValueStatusResp getAssetCostValueStatus(AssetCostValueStatusGetReq assetCostValueStatusGetReq);

    void saveAssetCostValueStatus(MultipartFile file,AssetCostValueStatusSaveReq assetCostValueStatusSave);
    
    void deactivateAssetCostValueStatus(AssetCostValueStatusDeactiveReq assetCostValueStatusDeactiveReq);

    AssetCostValueStatusDtlTO assetCostValueStatusDocDownloadFile(Long fileId);
    
    DocumentsResp getProjAssetCostValueDocuemnts(
            AssetCostValueStatusGetReq assetCostValueStatusGetReq);

}
