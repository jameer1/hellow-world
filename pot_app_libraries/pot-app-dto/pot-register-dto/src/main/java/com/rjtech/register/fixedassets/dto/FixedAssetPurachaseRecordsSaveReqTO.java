package com.rjtech.register.fixedassets.dto;

import com.rjtech.common.dto.ClientTO;

public class FixedAssetPurachaseRecordsSaveReqTO extends ClientTO {

    private static final long serialVersionUID = 2740448397989044791L;

    private FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO = new FixedAssetAqulisitionRecordsDtlTO();

    public FixedAssetAqulisitionRecordsDtlTO getFixedAssetAqulisitionRecordsDtlTO() {
        return fixedAssetAqulisitionRecordsDtlTO;
    }

    public void setFixedAssetAqulisitionRecordsDtlTO(
            FixedAssetAqulisitionRecordsDtlTO fixedAssetAqulisitionRecordsDtlTO) {
        this.fixedAssetAqulisitionRecordsDtlTO = fixedAssetAqulisitionRecordsDtlTO;
    }

}
