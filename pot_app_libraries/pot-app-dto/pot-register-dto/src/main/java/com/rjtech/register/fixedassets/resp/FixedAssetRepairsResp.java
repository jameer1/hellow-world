package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;

public class FixedAssetRepairsResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<FixedAssetRepairsRecordsDtlTO> fixedAssetRepairsRecordsDtlTOs = new ArrayList<FixedAssetRepairsRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetRepairsRecordsDtlTO> getFixedAssetRepairsRecordsDtlTOs() {
        return fixedAssetRepairsRecordsDtlTOs;
    }

    public void setFixedAssetRepairsRecordsDtlTOs(List<FixedAssetRepairsRecordsDtlTO> fixedAssetRepairsRecordsDtlTOs) {
        this.fixedAssetRepairsRecordsDtlTOs = fixedAssetRepairsRecordsDtlTOs;
    }


}
