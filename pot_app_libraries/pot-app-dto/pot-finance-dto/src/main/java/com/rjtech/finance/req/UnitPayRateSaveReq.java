package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.UnitPayRateTO;

public class UnitPayRateSaveReq extends ClientTO {

    private static final long serialVersionUID = -2471908090858291718L;
    private List<UnitPayRateTO> unitPayRateTOs = new ArrayList<UnitPayRateTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<UnitPayRateTO> getUnitPayRateTOs() {
        return unitPayRateTOs;
    }

    public void setUnitPayRateTOs(List<UnitPayRateTO> unitPayRateTOs) {
        this.unitPayRateTOs = unitPayRateTOs;
    }

}
