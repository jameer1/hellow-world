package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.AssetLifeStatusDtlTO;

public class AssetLifeStatusResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<AssetLifeStatusDtlTO> assetLifeStatusDtlTOs = new ArrayList<AssetLifeStatusDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<AssetLifeStatusDtlTO> getAssetLifeStatusDtlTOs() {
        return assetLifeStatusDtlTOs;
    }

    public void setAssetLifeStatusDtlTOs(List<AssetLifeStatusDtlTO> assetLifeStatusDtlTOs) {
        this.assetLifeStatusDtlTOs = assetLifeStatusDtlTOs;
    }

}
