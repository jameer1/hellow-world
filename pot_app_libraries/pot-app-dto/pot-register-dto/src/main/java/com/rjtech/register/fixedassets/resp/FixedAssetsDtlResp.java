package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAssetsDtlResp extends AppResp {

    private static final long serialVersionUID = 7170363069809971371L;

    private List<FixedAssetDtlTO> fixedAssetDtlTOs = new ArrayList<FixedAssetDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetDtlTO> getFixedAssetDtlTOs() {
        return fixedAssetDtlTOs;
    }

    public void setFixedAssetDtlTOs(List<FixedAssetDtlTO> fixedAssetDtlTOs) {
        this.fixedAssetDtlTOs = fixedAssetDtlTOs;
    }

}
