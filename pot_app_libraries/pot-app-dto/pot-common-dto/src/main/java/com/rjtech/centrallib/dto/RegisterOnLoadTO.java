package com.rjtech.centrallib.dto;

import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class RegisterOnLoadTO {

    private Map<Long, LabelKeyTO> companyMap = null;
    private Map<Long, LabelKeyTO> procureCatgMap = null;
    private Map<Long, LabelKeyTO> classificationMap = null;
    private Map<Long, LabelKeyTO> empClassificationMap = null;

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

    public Map<Long, LabelKeyTO> getClassificationMap() {
        return classificationMap;
    }

    public void setClassificationMap(Map<Long, LabelKeyTO> classificationMap) {
        this.classificationMap = classificationMap;
    }

    public Map<Long, LabelKeyTO> getEmpClassificationMap() {
        return empClassificationMap;
    }

    public void setEmpClassificationMap(Map<Long, LabelKeyTO> empClassificationMap) {
        this.empClassificationMap = empClassificationMap;
    }

}
