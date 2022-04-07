package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.CountryTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class FinanceOnLoadResp extends AppResp {

    private static final long serialVersionUID = -1239064339685714342L;

    private List<CountryTO> countryTOs = null;
    private Map<Long, LabelKeyTO> projEmpClassMap = null;

    public FinanceOnLoadResp() {
        countryTOs = new ArrayList<CountryTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projEmpClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CountryTO> getCountryTOs() {
        return countryTOs;
    }

    public void setCountryTOs(List<CountryTO> countryTOs) {
        this.countryTOs = countryTOs;
    }

    public Map<Long, LabelKeyTO> getProjEmpClassMap() {
        return projEmpClassMap;
    }

    public void setProjEmpClassMap(Map<Long, LabelKeyTO> projEmpClassMap) {
        this.projEmpClassMap = projEmpClassMap;
    }

}
