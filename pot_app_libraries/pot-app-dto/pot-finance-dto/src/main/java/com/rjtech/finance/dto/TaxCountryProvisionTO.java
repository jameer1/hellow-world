package com.rjtech.finance.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.LabelKeyTO;

public class TaxCountryProvisionTO extends ClientTO {

    private static final long serialVersionUID = -8938053975872588750L;

    private Long id;
    private String code;
    private LabelKeyTO countryLabelKeyTO = new LabelKeyTO();
    private LabelKeyTO provisionLabelKeyTO = new LabelKeyTO();
    private String fromDate;
    private String toDate;
    private String effectiveDate;
    private List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs = new ArrayList<TaxCodeCountryProvisionTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LabelKeyTO getCountryLabelKeyTO() {
        return countryLabelKeyTO;
    }

    public void setCountryLabelKeyTO(LabelKeyTO countryLabelKeyTO) {
        this.countryLabelKeyTO = countryLabelKeyTO;
    }

    public LabelKeyTO getProvisionLabelKeyTO() {
        return provisionLabelKeyTO;
    }

    public void setProvisionLabelKeyTO(LabelKeyTO provisionLabelKeyTO) {
        this.provisionLabelKeyTO = provisionLabelKeyTO;
    }

    public List<TaxCodeCountryProvisionTO> getTaxCodeCountryProvisionTOs() {
        return taxCodeCountryProvisionTOs;
    }

    public void setTaxCodeCountryProvisionTOs(List<TaxCodeCountryProvisionTO> taxCodeCountryProvisionTOs) {
        this.taxCodeCountryProvisionTOs = taxCodeCountryProvisionTOs;
    }

}
