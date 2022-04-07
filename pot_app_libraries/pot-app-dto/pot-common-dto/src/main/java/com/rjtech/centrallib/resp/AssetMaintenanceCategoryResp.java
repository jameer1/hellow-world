package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AssetMaintenanceCategoryTO;
import com.rjtech.common.resp.AppResp;

public class AssetMaintenanceCategoryResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs = new ArrayList<AssetMaintenanceCategoryTO>();

    public List<AssetMaintenanceCategoryTO> getAssetMaintenanceCategoryTOs() {
        return assetMaintenanceCategoryTOs;
    }

    public void setAssetMaintenanceCategoryTOs(List<AssetMaintenanceCategoryTO> assetMaintenanceCategoryTOs) {
        this.assetMaintenanceCategoryTOs = assetMaintenanceCategoryTOs;
    }
}
