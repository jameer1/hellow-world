package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.UnitPayRateTO;

public class UnitPayRateGetReq extends ClientTO {

    private static final long serialVersionUID = 8038853297550198605L;

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<UnitPayRateTO> unitPayRateTOs = new ArrayList<UnitPayRateTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<UnitPayRateTO> getUnitPayRateTOs() {
        return unitPayRateTOs;
    }

    public void setUnitPayRateTOs(List<UnitPayRateTO> unitPayRateTOs) {
        this.unitPayRateTOs = unitPayRateTOs;
    }

}
