package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class TaxCodeCountryProvisionTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8938053975872588750L;

    private Long id;
    private String dueDate;
    private String periodCycle;
    private Long taxCountryProvsionId;
    private TaxCodesTO taxCodesTO = new TaxCodesTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaxCountryProvsionId() {
        return taxCountryProvsionId;
    }

    public void setTaxCountryProvsionId(Long taxCountryProvsionId) {
        this.taxCountryProvsionId = taxCountryProvsionId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPeriodCycle() {
        return periodCycle;
    }

    public void setPeriodCycle(String periodCycle) {
        this.periodCycle = periodCycle;
    }

    public TaxCodesTO getTaxCodesTO() {
        return taxCodesTO;
    }

    public void setTaxCodesTO(TaxCodesTO taxCodesTO) {
        this.taxCodesTO = taxCodesTO;
    }

}
