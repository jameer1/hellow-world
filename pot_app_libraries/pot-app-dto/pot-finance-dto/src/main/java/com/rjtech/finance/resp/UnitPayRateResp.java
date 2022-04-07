package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.UnitPayRateTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class UnitPayRateResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<UnitPayRateTO> unitPayRateTOs = null;

    public UnitPayRateResp() {
        unitPayRateTOs = new ArrayList<UnitPayRateTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<UnitPayRateTO> getUnitPayRateTOs() {
        return unitPayRateTOs;
    }

    public void setUnitPayRateTOs(List<UnitPayRateTO> unitPayRateTOs) {
        this.unitPayRateTOs = unitPayRateTOs;
    }

}
