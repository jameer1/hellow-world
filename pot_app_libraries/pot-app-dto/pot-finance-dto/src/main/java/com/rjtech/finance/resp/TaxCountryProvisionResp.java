package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxCountryProvisionTO;

public class TaxCountryProvisionResp extends AppResp {

    private static final long serialVersionUID = 5228532470619377619L;

    private List<TaxCountryProvisionTO> taxCountryProvisionTOs = null;
    private Map<Long, LabelKeyTO> countryMap = null;
    private Map<Long, LabelKeyTO> provisionMap = null;

    public TaxCountryProvisionResp() {
        taxCountryProvisionTOs = new ArrayList<TaxCountryProvisionTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        countryMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        provisionMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxCountryProvisionTO> getTaxCountryProvisionTOs() {
        return taxCountryProvisionTOs;
    }

    public void setTaxCountryProvisionTOs(List<TaxCountryProvisionTO> taxCountryProvisionTOs) {
        this.taxCountryProvisionTOs = taxCountryProvisionTOs;
    }

    public Map<Long, LabelKeyTO> getCountryMap() {
        return countryMap;
    }

    public void setCountryMap(Map<Long, LabelKeyTO> countryMap) {
        this.countryMap = countryMap;
    }

    public Map<Long, LabelKeyTO> getProvisionMap() {
        return provisionMap;
    }

    public void setProvisionMap(Map<Long, LabelKeyTO> provisionMap) {
        this.provisionMap = provisionMap;
    }

}
