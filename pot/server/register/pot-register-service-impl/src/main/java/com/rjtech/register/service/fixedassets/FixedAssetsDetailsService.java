package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.FixedAssetRegDeactivateReq;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.FixedAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;

public interface FixedAssetsDetailsService {

    FixedAssetsRegisterOnLoadResp getFixedAssetRegister(FixedAssetsGetReq fixedAssetsGetReq);

    void fixedAssetRegistersDeactivate(FixedAssetRegDeactivateReq fixedAssetRegDeactivateReq);

    FixedAssetsRegisterOnLoadResp getAssetSubById(FixedAssetsGetReq fixedAssetsGetReq);

    void saveSubAsset(FixedAssetsSaveReq fixedAssetsSaveReq);

}
