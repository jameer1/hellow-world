package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.finance.dto.TaxCountryProvisionTO;

public class TaxCountryProvisionSaveReq extends ClientTO {

    private static final long serialVersionUID = -4819561725479979123L;
    private TaxCountryProvisionTO taxCountryProvisionTO = new TaxCountryProvisionTO();
    private Long countryId;

    public TaxCountryProvisionTO getTaxCountryProvisionTO() {
        return taxCountryProvisionTO;
    }

    public void setTaxCountryProvisionTO(TaxCountryProvisionTO taxCountryProvisionTO) {
        this.taxCountryProvisionTO = taxCountryProvisionTO;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

}
