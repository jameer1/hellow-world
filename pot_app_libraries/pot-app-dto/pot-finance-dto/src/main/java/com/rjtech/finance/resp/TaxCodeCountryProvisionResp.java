package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxCodeCountryProvisionTO;

public class TaxCodeCountryProvisionResp extends AppResp {

    private static final long serialVersionUID = 5228532470619377619L;

    private List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs = null;
    private Map<Long, LabelKeyTO> projEmpClassMap = null;
    private Map<Long, LabelKeyTO> procureCategoryMap = null;
    private List<String> periodCycles = null;

    public TaxCodeCountryProvisionResp() {
        taxCodeCountryProvisionTOs = new ArrayList<TaxCodeCountryProvisionTO>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projEmpClassMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        procureCategoryMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        periodCycles = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxCodeCountryProvisionTO> getTaxCodeCountryProvisionTOs() {
        return taxCodeCountryProvisionTOs;
    }

    public void setTaxCodeCountryProvisionTOs(List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs) {
        this.taxCodeCountryProvisionTOs = taxCodeCountryProvisionTOs;
    }

    public Map<Long, LabelKeyTO> getProjEmpClassMap() {
        return projEmpClassMap;
    }

    public void setProjEmpClassMap(Map<Long, LabelKeyTO> projEmpClassMap) {
        this.projEmpClassMap = projEmpClassMap;
    }

    public Map<Long, LabelKeyTO> getProcureCategoryMap() {
        return procureCategoryMap;
    }

    public void setProcureCategoryMap(Map<Long, LabelKeyTO> procureCategoryMap) {
        this.procureCategoryMap = procureCategoryMap;
    }

    public List<String> getPeriodCycles() {
        return periodCycles;
    }

    public void setPeriodCycles(List<String> periodCycles) {
        this.periodCycles = periodCycles;
    }

}
