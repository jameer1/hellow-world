package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.FixedAssetAqulisitionRecordsDtlTO;

public class AssetPurchaseDeactiveReq extends FixedAssetAqulisitionRecordsDtlTO {

    private static final long serialVersionUID = -7671175298681215590L;
    private List<Long> purchaseRecordsIds = new ArrayList<Long>();

    public List<Long> getPurchaseRecordsIds() {
        return purchaseRecordsIds;
    }

    public void setPurchaseRecordsIds(List<Long> purchaseRecordsIds) {
        this.purchaseRecordsIds = purchaseRecordsIds;
    }

}
