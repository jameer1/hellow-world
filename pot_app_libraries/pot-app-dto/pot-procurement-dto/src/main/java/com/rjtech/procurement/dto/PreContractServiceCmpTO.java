package com.rjtech.procurement.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class PreContractServiceCmpTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -7970282420794044833L;

    private Long id;
    private Long preContractCmpId;
    private Long serviceDtlId;
    private BigDecimal rate;
    private String comments;
    private boolean approveFlag;
    private Integer quantity;
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getServiceDtlId() {
        return serviceDtlId;
    }

    public void setServiceDtlId(Long serviceDtlId) {
        this.serviceDtlId = serviceDtlId;
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

    public boolean isApproveFlag() {
        return approveFlag;
    }

    public void setApproveFlag(boolean approveFlag) {
        this.approveFlag = approveFlag;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
