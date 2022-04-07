package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeactiveReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusDeleteReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusGetReq;
import com.rjtech.register.fixedassets.req.AssetLifeStatusSaveReq;
import com.rjtech.register.fixedassets.resp.AssetLifeStatusResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;

public interface AssetLifeStatusService {

    AssetLifeStatusResp getAssetLifeStatus(AssetLifeStatusGetReq assetLifeStatusGetReq);

    void saveAssetLifeStatus(MultipartFile file, AssetLifeStatusSaveReq assetLifeStatusSave);

    void assetLifeStatusDelete(AssetLifeStatusDeleteReq assetLifeStatusDeleteReq);

    void deactivateAssetLifeStatus(AssetLifeStatusDeactiveReq assetLifeStatusDeactiveReq);

    AssetLifeStatusDtlTO assetLifeStatusDocDownloadFile(Long fileId);
    
    DocumentsResp getProjAssetLifeStatusDocuemnts(
            AssetLifeStatusGetReq assetLifeStatusGetReq);

}
