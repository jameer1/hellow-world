package com.rjtech.centrallib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class EmpOnLoadInCentLibResp {

    private Map<Long, LabelKeyTO> companyMap = new HashMap<Long, LabelKeyTO>();;

    private Map<Long, LabelKeyTO> procureCatgMap = new HashMap<Long, LabelKeyTO>();

    private Map<Long, LabelKeyTO> empClassMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

    public Map<Long, LabelKeyTO> getProcureCatgMap() {
        return procureCatgMap;
    }

    public void setProcureCatgMap(Map<Long, LabelKeyTO> procureCatgMap) {
        this.procureCatgMap = procureCatgMap;
    }

    public Map<Long, LabelKeyTO> getEmpClassMap() {
        return empClassMap;
    }

    public void setEmpClassMap(Map<Long, LabelKeyTO> empClassMap) {
        this.empClassMap = empClassMap;
    }

}
