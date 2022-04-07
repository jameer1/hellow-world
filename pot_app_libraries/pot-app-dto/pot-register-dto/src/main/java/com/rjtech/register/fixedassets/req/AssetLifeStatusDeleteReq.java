package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;

public class AssetLifeStatusDeleteReq extends AssetLifeStatusDtlTO {

    private static final long serialVersionUID = -4543819922890869538L;
    List<Long> assetLifeStatusIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getAssetLifeStatusIds() {
        return assetLifeStatusIds;
    }

    public void setAssetLifeStatusIds(List<Long> assetLifeStatusIds) {
        this.assetLifeStatusIds = assetLifeStatusIds;
    }

}
