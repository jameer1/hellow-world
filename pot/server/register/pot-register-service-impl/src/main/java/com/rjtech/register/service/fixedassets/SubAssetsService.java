package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;

public interface SubAssetsService {

    SubAssetsResp getSubAssets(SubAssetsGetReq subAssetsGetReq);

    void saveSubAssets(SubAssetsSaveReq subAssetsSaveReq);

    void deactivateSubAsset(SubAssetDeleteReq subAssetDeleteReq);

    void subAssetDelete(SubAssetDeleteReq subAssetDeleteReq);

    SubAssetsResp getAssetById(SubAssetsGetReq subAssetsGetReq);

}
