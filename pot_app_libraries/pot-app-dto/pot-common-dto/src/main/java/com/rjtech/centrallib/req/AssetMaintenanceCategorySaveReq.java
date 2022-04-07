package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetMaintenanceCategoryTO;
import com.rjtech.common.dto.ClientTO;


public class AssetMaintenanceCategorySaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs = new ArrayList<AssetMaintenanceCategoryTO>(
            5);

    public List<AssetMaintenanceCategoryTO> getAssetMaintenanceCategoryTOs() {
        return assetMaintenanceCategoryTOs;
    }

    public void setAssetMaintenanceCategoryTOs(List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs) {
        this.assetMaintenanceCategoryTOs = assetMaintenanceCategoryTOs;
    }
}
