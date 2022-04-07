package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;

public class FixedAssetRegDeactivateReq extends FixedAssetDtlTO {
    private static final long serialVersionUID = -7287656828700438515L;
    List<Long> fixedAssetRegIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getFixedAssetRegIds() {
        return fixedAssetRegIds;
    }

    public void setFixedAssetRegIds(List<Long> fixedAssetRegIds) {
        this.fixedAssetRegIds = fixedAssetRegIds;
    }

}
