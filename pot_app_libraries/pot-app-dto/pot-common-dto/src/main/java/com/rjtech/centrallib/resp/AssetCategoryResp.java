package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetCategoryTO;
import com.rjtech.common.resp.AppResp;

public class AssetCategoryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<AssetCategoryTO> assetCategoryTOs = new ArrayList<AssetCategoryTO>();

    public List<AssetCategoryTO> getAssetCategoryTOs() {
        return assetCategoryTOs;
    }

    public void setAssetCategoryTOs(List<AssetCategoryTO> assetCategoryTOs) {
        this.assetCategoryTOs = assetCategoryTOs;
    }

}
