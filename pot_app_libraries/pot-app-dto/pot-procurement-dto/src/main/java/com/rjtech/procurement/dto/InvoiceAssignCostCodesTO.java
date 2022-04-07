package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class InvoiceAssignCostCodesTO extends ProjectTO {

    private static final long serialVersionUID = -5434339998025126242L;
    private Long id;
    private Long purId;
    private Long costId;
    private Integer split;
    private BigDecimal amount;
    private Integer status;

    public InvoiceAssignCostCodesTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}