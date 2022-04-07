package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class TaxOnSuperFundTaxTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3163061445781755634L;

    private Long id;
    private Double limitIncome;
    private Double fundAmount;
    private Double taxRate;
    private String comments;
    private Long taxCodeCountryProvisionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimitIncome() {
        return limitIncome;
    }

    public void setLimitIncome(Double limitIncome) {
        this.limitIncome = limitIncome;
    }

    public Double getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(Double fundAmount) {
        this.fundAmount = fundAmount;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
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
