package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;
import com.rjtech.register.fixedassets.req.AssetPurchaseDeactiveReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetPurachaseRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.FixedAssetPurachseAcqulistionResp;

public interface FixedAssetsPurachaseRecordsService {

    FixedAssetPurachseAcqulistionResp getFixedAssetsPurachaseAcqulisition(
            FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq);

    void saveFixedAssetsPurachaseAcqulisition(MultipartFile file,
            FixedAssetPurachaseRecordsSaveReq fixedAssetPurachaseRecordsSave);

    void deactivateAssetsPurachase(AssetPurchaseDeactiveReq purchaseDeactiveReq);

    FixedAssetAqulisitionRecordsDtlTO getAssetRecordFileInfo(Long purchaseRecordId);
    
    DocumentsResp getProjPurchaseDocuemnts(
            FixedAssetPurachaseRecordsGetReq fixedAssetPurachaseRecordsGetReq);
}
