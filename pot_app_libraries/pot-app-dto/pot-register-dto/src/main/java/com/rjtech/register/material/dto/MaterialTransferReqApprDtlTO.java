package com.rjtech.register.material.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class MaterialTransferReqApprDtlTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long materialTransId;
    private Long materialId;
    private String reqComments;
    private String apprComments;
    private String apprStatus;
    private BigDecimal reqQty;
    private BigDecimal apprQty;
    private String reqDate;
    private String apprDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialTransId() {
        return materialTransId;
    }

    public void setMaterialTransId(Long materialTransId) {
        this.materialTransId = materialTransId;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public BigDecimal getReqQty() {
        return reqQty;
    }

    public void setReqQty(BigDecimal reqQty) {
        this.reqQty = reqQty;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getApprDate() {
        return apprDate;
    }

    public void setApprDate(String apprDate) {
        this.apprDate = apprDate;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public BigDecimal getApprQty() {
        return apprQty;
    }

    public void setApprQty(BigDecimal apprQty) {
        this.apprQty = apprQty;
    }

}
