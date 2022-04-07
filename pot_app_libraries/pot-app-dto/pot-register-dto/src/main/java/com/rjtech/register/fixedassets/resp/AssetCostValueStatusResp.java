package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetCostValueStatusDtlTO;

public class AssetCostValueStatusResp extends AppResp {
    private static final long serialVersionUID = 1L;
    private List<AssetCostValueStatusDtlTO> assetCostValueStatusDtlTOs = new ArrayList<AssetCostValueStatusDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<AssetCostValueStatusDtlTO> getAssetCostValueStatusDtlTOs() {
        return assetCostValueStatusDtlTOs;
    }

    public void setAssetCostValueStatusDtlTOs(List<AssetCostValueStatusDtlTO> assetCostValueStatusDtlTOs) {
        this.assetCostValueStatusDtlTOs = assetCostValueStatusDtlTOs;
    }

}
