package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceMaterialGetReq extends ProjectTO {

    private static final long serialVersionUID = 4782807246932099144L;
    private Long purchaseId;
    private String invoceFromDate;
    private String invoceToDate;
    private Long companyId;
    private Long precontractId;
    private BigDecimal amount;
    private Integer paymentInDays;
    private String procureType;
    private String completeProcureType;
    private Date createdOn;

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getInvoceFromDate() {
        return invoceFromDate;
    }

    public void setInvoceFromDate(String invoceFromDate) {
        this.invoceFromDate = invoceFromDate;
    }

    public String getInvoceToDate() {
        return invoceToDate;
    }

    public void setInvoceToDate(String invoceToDate) {
        this.invoceToDate = invoceToDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPaymentInDays() {
        return paymentInDays;
    }

    public void setPaymentInDays(Integer paymentInDays) {
        this.paymentInDays = paymentInDays;
    }

    public String getProcureType() {
        return procureType;
    }

    public void setProcureType(String procureType) {
        this.procureType = procureType;
    }

    public String getCompleteProcureType() {
        return completeProcureType;
    }

    public void setCompleteProcureType(String completeProcureType) {
        this.completeProcureType = completeProcureType;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    public Long getPrecontractId() {
        return precontractId;
    }

    public void setPrecontractId(Long precontractId) {
        this.precontractId = precontractId;
    }
  
}
