package com.rjtech.finance.dto;

import com.rjtech.common.dto.ClientTO;

public class ServiceTaxTO extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7198552036410732741L;

    private Long id;
    private Double invoiceAmount;
    private Double taxRate;
    private String comments;
    private Long taxCodeCountryProvisionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
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
