package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;

public class FixedAssetPurachseAcqulistionResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTOs = new ArrayList<FixedAssetAqulisitionRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<FixedAssetAqulisitionRecordsDtlTO> getFixedAssetAqulisitionRecordsDtlTOs() {
        return fixedAssetAqulisitionRecordsDtlTOs;
    }

    public void setFixedAssetAqulisitionRecordsDtlTOs(
            List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTOs) {
        this.fixedAssetAqulisitionRecordsDtlTOs = fixedAssetAqulisitionRecordsDtlTOs;
    }

}
