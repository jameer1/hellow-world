package com.rjtech.centrallib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class EmpClassMapResp {

    Map<Long, LabelKeyTO> empClassMap = new HashMap<Long, LabelKeyTO>();
    Map<Long, LabelKeyTO> wageFactorMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getEmpClassMap() {
        return empClassMap;
    }

    public void setEmpClassMap(Map<Long, LabelKeyTO> empClassMap) {
        this.empClassMap = empClassMap;
    }

    public Map<Long, LabelKeyTO> getWageFactorMap() {
        return wageFactorMap;
    }

    public void setWageFactorMap(Map<Long, LabelKeyTO> wageFactorMap) {
        this.wageFactorMap = wageFactorMap;
    }

}
