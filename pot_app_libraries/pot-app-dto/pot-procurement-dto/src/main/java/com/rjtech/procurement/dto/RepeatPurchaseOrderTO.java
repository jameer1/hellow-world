package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

import java.util.Date;

public class RepeatPurchaseOrderTO extends ProjectTO {

    private static final long serialVersionUID = 44553L;

    private Long id;

    private Long parentPOId;

    private Long projId;

    private String deliveryPlace;

    private Long quantity;

    private Date startDate;

    private Date finishDate;

    private Integer isBid;

    private String createdBy;

    private Date createdOn;

    private String updatedBy;

    private Date updatedOn;

    private Long contractId;

    private Long contractCmpId;

    private Long contractItemId;

    private Long contractItemDetailId;

    private String contractItemType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentPOId() {
        return parentPOId;
    }

    public void setParentPOId(Long parentPOId) {
        this.parentPOId = parentPOId;
    }

    @Override
    public Long getProjId() {
        return projId;
    }

    @Override
    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getIsBid() {
        return isBid;
    }

    public void setIsBid(Integer isBid) {
        this.isBid = isBid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getContractCmpId() {
        return contractCmpId;
    }

    public void setContractCmpId(Long contractCmpId) {
        this.contractCmpId = contractCmpId;
    }

    public Long getContractItemId() {
        return contractItemId;
    }

    public void setContractItemId(Long contractItemId) {
        this.contractItemId = contractItemId;
    }

    public Long getContractItemDetailId() {
        return contractItemDetailId;
    }

    public void setContractItemDetailId(Long contractItemDetailId) {
        this.contractItemDetailId = contractItemDetailId;
    }

    public String getContractItemType() {
        return contractItemType;
    }

    public void setContractItemType(String contractItemType) {
        this.contractItemType = contractItemType;
    }
}
