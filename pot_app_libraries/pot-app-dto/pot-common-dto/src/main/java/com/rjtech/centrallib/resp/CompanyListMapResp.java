package com.rjtech.centrallib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class CompanyListMapResp {

    Map<Long, LabelKeyTO> CompanyListMap = new HashMap<Long, LabelKeyTO>();

    public Map<Long, LabelKeyTO> getCompanyListMap() {
        return CompanyListMap;
    }

    public void setCompanyListMap(Map<Long, LabelKeyTO> companyListMap) {
        CompanyListMap = companyListMap;
    }

}
