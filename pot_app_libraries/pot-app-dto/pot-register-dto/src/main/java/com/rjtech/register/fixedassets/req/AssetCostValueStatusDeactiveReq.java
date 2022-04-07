package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;

public class AssetCostValueStatusDeactiveReq extends AssetCostValueStatusDtlTO {

    private static final long serialVersionUID = -4543819922890869538L;
    List<Long> assetCostValueStatusIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getAssetCostValueStatusIds() {
        return assetCostValueStatusIds;
    }

    public void setAssetCostValueStatusIds(List<Long> assetCostValueStatusIds) {
        this.assetCostValueStatusIds = assetCostValueStatusIds;
    }


}
