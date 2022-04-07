package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PreContractMaterialCmpTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7970282420794044833L;

    private Long id;
    private Long preContractCmpId;
    private Long preContractMaterialId;
    private BigDecimal rate;
    private String comments;
    private Integer quantity;
    private boolean approveFlag;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(boolean approveFlag) {
        this.approveFlag = approveFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getPreContractMaterialId() {
        return preContractMaterialId;
    }

    public void setPreContractMaterialId(Long preContractMaterialId) {
        this.preContractMaterialId = preContractMaterialId;
    }

}
