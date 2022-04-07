package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class UnitPayRateDelReq extends ClientTO {

    private static final long serialVersionUID = -7252835744141909230L;
    private List<Long> unitPayRateIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getUnitPayRateIds() {
        return unitPayRateIds;
    }

    public void setUnitPayRateIds(List<Long> unitPayRateIds) {
        this.unitPayRateIds = unitPayRateIds;
    }

}
