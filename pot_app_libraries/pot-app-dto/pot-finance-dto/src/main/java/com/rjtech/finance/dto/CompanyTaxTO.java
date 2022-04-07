package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class CompanyTaxTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2065771963572839334L;

    private Long id;
    private Double annualMinTax;
    private Double annualMaxTax;
    private Double fixedTax;
    private Double variableTax;
    private String comments;
    private Long taxCodeCountryProvisionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAnnualMinTax() {
        return annualMinTax;
    }

    public void setAnnualMinTax(Double annualMinTax) {
        this.annualMinTax = annualMinTax;
    }

    public Double getAnnualMaxTax() {
        return annualMaxTax;
    }

    public void setAnnualMaxTax(Double annualMaxTax) {
        this.annualMaxTax = annualMaxTax;
    }

    public Double getFixedTax() {
        return fixedTax;
    }

    public void setFixedTax(Double fixedTax) {
        this.fixedTax = fixedTax;
    }

    public Double getVariableTax() {
        return variableTax;
    }

    public void setVariableTax(Double variableTax) {
        this.variableTax = variableTax;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getTaxCodeCountryProvisionId() {
        return taxCodeCountryProvisionId;
    }

    public void setTaxCodeCountryProvisionId(Long taxCodeCountryProvisionId) {
        this.taxCodeCountryProvisionId = taxCodeCountryProvisionId;
    }

}
