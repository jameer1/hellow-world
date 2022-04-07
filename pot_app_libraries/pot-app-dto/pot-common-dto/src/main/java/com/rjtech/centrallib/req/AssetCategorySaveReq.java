package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetCategoryTO;
import com.rjtech.common.dto.ClientTO;


public class AssetCategorySaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<AssetCategoryTO> assetCategoryTOs = new ArrayList<AssetCategoryTO>(
            5);

    public List<AssetCategoryTO> getAssetCategoryTOs() {
        return assetCategoryTOs;
    }

    public void setAssetCategoryTOs(List<AssetCategoryTO> assetCategoryTOs) {
        this.assetCategoryTOs = assetCategoryTOs;
    }

}
