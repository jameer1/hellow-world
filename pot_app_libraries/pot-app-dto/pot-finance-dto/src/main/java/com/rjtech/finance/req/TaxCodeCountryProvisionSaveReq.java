package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxCodeCountryProvisionTO;

public class TaxCodeCountryProvisionSaveReq extends ClientTO {

    private static final long serialVersionUID = -4819561725479979123L;
    private List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs = new ArrayList<TaxCodeCountryProvisionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long taxId;

    public List<TaxCodeCountryProvisionTO> getTaxCodeCountryProvisionTOs() {
        return taxCodeCountryProvisionTOs;
    }

    public void setTaxCodeCountryProvisionTOs(List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs) {
        this.taxCodeCountryProvisionTOs = taxCodeCountryProvisionTOs;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

}
