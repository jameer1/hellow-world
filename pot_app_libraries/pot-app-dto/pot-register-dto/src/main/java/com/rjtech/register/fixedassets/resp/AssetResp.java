package com.rjtech.register.fixedassets.resp;

import java.util.List;

import com.rjtech.common.resp.AppResp;

public class AssetResp extends AppResp {
    private static final long serialVersionUID = -7671175298681215590L;
    //private List<ClientTO> clientAsset = null;
    private List<Long> newAssetIds = null;

    public List<Long> getNewAssetIds() {
        return newAssetIds;
    }

    public void setNewAssetIds(List<Long> newAssetIds) {
        this.newAssetIds = newAssetIds;
    }

}
