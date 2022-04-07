package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.FixedAssetRepairsRecordsDtlTO;

public class AssetRepairsDeactiveReq extends FixedAssetRepairsRecordsDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> repairRecordsIds = new ArrayList<Long>();
    public List<Long> getRepairRecordsIds() {
        return repairRecordsIds;
    }
    public void setRepairRecordsIds(List<Long> repairRecordsIds) {
        this.repairRecordsIds = repairRecordsIds;
    }


}
