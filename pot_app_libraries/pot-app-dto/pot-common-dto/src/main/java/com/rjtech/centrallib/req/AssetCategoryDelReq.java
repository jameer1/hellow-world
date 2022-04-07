package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class AssetCategoryDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<Long> assetIds = new ArrayList<Long>(5);

    public List<Long> getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(List<Long> assetIds) {
        this.assetIds = assetIds;
    }
}
