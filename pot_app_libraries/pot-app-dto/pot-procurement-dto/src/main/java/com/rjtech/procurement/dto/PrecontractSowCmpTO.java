package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PrecontractSowCmpTO extends ProjectTO {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long quantity;
    private BigDecimal rate;
    private String comments;
    private Integer version;
    private Long preContractSowDtlId;
    private Long preContractCmpId;
    private boolean approveFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getPreContractSowDtlId() {
        return preContractSowDtlId;
    }

    public void setPreContractSowDtlId(Long preContractSowDtlId) {
        this.preContractSowDtlId = preContractSowDtlId;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public boolean isApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(boolean approveFlag) {
        this.approveFlag = approveFlag;
    }

}
