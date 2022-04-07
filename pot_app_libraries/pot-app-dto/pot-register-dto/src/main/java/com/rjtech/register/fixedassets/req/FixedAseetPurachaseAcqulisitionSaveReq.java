package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAseetPurachaseAcqulisitionSaveReq extends FixedAssetAqulisitionRecordsDtlTO {

    private static final long serialVersionUID = 1L;

    public FixedAseetPurachaseAcqulisitionSaveReq() {

    }

    private FixedAssetDtlTO fixedAssetDtlTO;

    private List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTOs = new ArrayList<FixedAssetAqulisitionRecordsDtlTO>();

    public FixedAssetDtlTO getFixedAssetDtlTO() {
        return fixedAssetDtlTO;
    }

    public void setFixedAssetDtlTO(FixedAssetDtlTO fixedAssetDtlTO) {
        this.fixedAssetDtlTO = fixedAssetDtlTO;
    }

    public List<FixedAssetAqulisitionRecordsDtlTO> getFixedAssetAqulisitionRecordsDtlTOs() {
        return fixedAssetAqulisitionRecordsDtlTOs;
    }

    public void setFixedAssetAqulisitionRecordsDtlTOs(
            List<FixedAssetAqulisitionRecordsDtlTO> fixedAssetAqulisitionRecordsDtlTOs) {
        this.fixedAssetAqulisitionRecordsDtlTOs = fixedAssetAqulisitionRecordsDtlTOs;
    }

}
