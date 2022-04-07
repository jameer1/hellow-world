package com.rjtech.register.material.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class MaterialProjLedgerTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long regMaterialId;
    private BigDecimal openBalance;
    private BigDecimal credit;
    private BigDecimal debit;
    private BigDecimal availableBalance;
    private BigDecimal transit;
    private BigDecimal total;
    private String effectiveDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegMaterialId() {
        return regMaterialId;
    }

    public void setRegMaterialId(Long regMaterialId) {
        this.regMaterialId = regMaterialId;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getTransit() {
        return transit;
    }

    public void setTransit(BigDecimal transit) {
        this.transit = transit;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

}
